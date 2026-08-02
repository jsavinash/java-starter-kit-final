# Cline Token Optimization — Analysis & Integration

## 1. Token Consumption Sources in Cline

| Source | Estimated Tokens | Status | Action |
|---|---|---|---|
| System prompt (Cline core) | ~8,000 | Fixed | N/A |
| Tool definitions (built-in) | ~5,000 | Fixed | N/A |
| MCP server tool definitions | ~200-500 per server | **17 servers = ~6,800** | **Trim to 9** |
| `.clinerules` (always loaded) | ~1,200 (118 lines) | Optimizable | **Compress to ~60 lines** |
| `MEMORY_BANK.md` (per rules) | ~2,500 (273 lines) | Auto-loaded | **Make on-demand only** |
| `environment_details` file list | ~3,000 (truncated) | Auto-generated | N/A |
| Conversation history | Variable | Grows per turn | Use `token-optimizer` MCP |

## 2. Optimization Strategy

### 2.1 Trim MCP Servers (17 → 9)
**Problem**: Each MCP server adds 200-500 tokens of tool definitions to the system prompt. 17 servers = ~6,800 tokens wasted on tools that may never be used.

**Solution**: Keep only the 9 most valuable key-free servers. Remove env-gated servers that add overhead without functioning.

| Keep | Server | Reason |
|---|---|---|
| ✅ | `memory` | Core knowledge graph |
| ✅ | `sequential-thinking` | Reasoning support |
| ✅ | `filesystem` | File operations |
| ✅ | `parallel-search` | Key-free web search |
| ✅ | `context7` | Documentation lookup |
| ✅ | `playwright` | Browser automation |
| ✅ | `squish` | Local persistent memory (Cline-native) |
| ✅ | `token-optimizer` | Context compression (meta-optimization) |
| ✅ | `chrome-devtools` | Browser debugging |

| Remove | Server | Reason |
|---|---|---|
| ❌ | `github` | Env-gated, adds overhead without key |
| ❌ | `exa-web-search` | Env-gated, adds overhead without key |
| ❌ | `firecrawl` | Env-gated, adds overhead without key |
| ❌ | `vercel` | HTTP, rarely needed for Java project |
| ❌ | `clickhouse` | HTTP, rarely needed for Java project |
| ❌ | `cloudflare-docs` | HTTP, rarely needed for Java project |
| ❌ | `magic` | UI components, not relevant to Java backend |
| ❌ | `railway` | Deployments, not relevant to local dev |

**Savings**: ~3,200 tokens per session (8 servers × ~400 tokens each)

### 2.2 Compress `.clinerules` (118 → ~60 lines)
**Problem**: 118 lines = ~1,200 tokens loaded every session. Much of it is verbose explanations.

**Solution**: Compress to essential rules only — bullet points, no code examples, no verbose explanations.

**Savings**: ~600 tokens per session

### 2.3 Make MEMORY_BANK.md On-Demand
**Problem**: `.clinerules` says "Read MEMORY_BANK.md at session start" — 273 lines = ~2,500 tokens consumed immediately.

**Solution**: Change rule to "Read MEMORY_BANK.md only when project context is needed" — defer loading until actually required.

**Savings**: ~2,500 tokens per session (when not needed)

### 2.4 Use `token-optimizer` MCP
**Problem**: Long conversations accumulate tokens in history.

**Solution**: The `token-optimizer` MCP provides 95%+ context reduction via content deduplication and compression. It's already in the config.

### 2.5 Use `squish` MCP for Persistent Memory
**Problem**: Re-reading files across sessions wastes tokens.

**Solution**: `squish` provides local-first persistent memory (SQLite-backed, Cline-compatible). Store key facts once, recall in 1-20ms without re-reading files.

## 3. Total Estimated Savings

| Optimization | Tokens Saved | Per Session |
|---|---|---|
| Trim MCP servers (17→9) | ~3,200 | Every session |
| Compress `.clinerules` | ~600 | Every session |
| Defer MEMORY_BANK.md | ~2,500 | When not needed |
| **Total** | **~6,300** | **Per session** |

## 4. Implementation

### 4.1 Updated MCP Configuration
Trimmed from 17 to 9 servers (removed 8 env-gated/irrelevant servers).

### 4.2 Compressed `.clinerules`
Reduced from 118 to ~60 lines — essential rules only.

### 4.3 Deferred MEMORY_BANK.md Loading
Changed from "read at session start" to "read when needed".
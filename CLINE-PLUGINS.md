# Claude Code Plugins — Cline Compatibility Analysis & Integration

> **Scope**: Analysis of famous Claude Code plugin ecosystems and their compatibility with Cline (via MCP), followed by actual integration into Cline's MCP configuration.

## 1. What Are "Claude Code Plugins" Compatible with Cline?

Claude Code plugins operate in two layers:
- **Skills/Commands/Agents** — harness-native (Slash commands, custom prompts) — **NOT directly portable** to Cline
- **MCP Servers** — standard Model Context Protocol servers — **FULLY portable** to Cline because both Claude Code and Cline consume the same MCP protocol

The `cline_mcp_settings.json` file is the integration point: any MCP server running as `stdio` or `http` can be shared between Claude Code and Cline.

## 2. Famous Claude Code Plugin Ecosystems Analyzed

### 2.1 ECC — Everything Claude Code (v2.1.0)
**Marketplace**: `affaan-m/everything-claude-code`  
**Status**: INSTALLED (`~/.claude/plugins/marketplaces/ecc`)  
**Components**:

| Component | Count | Cline-Portable? |
|---|---|---|
| Agents | 67 | ❌ (harness-native). Cline re-implements via context |
| Skills | 281 | ⚠️ Partial — skills are prompt files; value carried via `.clinerules` or Cline tasks |
| Legacy Commands | 94 | ❌ (Claude Code slash commands) |
| MCP Servers | 30+ | ✅ **FULLY PORTABLE** via `cline_mcp_settings.json` |
| Hooks | ✓ | ❌ (Claude Code lifecycle hooks) |
| Rules | ✓ | ⚠️ Partial — can be ported to `.clinerules` |

**MCP Catalog** (`mcp-configs/mcp-servers.json`) is the primary integration vehicle.

### 2.2 Standard MCP Servers (`@modelcontextprotocol/*`)
Official reference servers from the MCP project. All use `stdio` and are fully Cline-compatible:

| Server | Purpose | Cline Status |
|---|---|---|
| `server-memory` | Persistent knowledge graph | ✅ Already integrated |
| `server-sequential-thinking` | Chain-of-thought reasoning | ✅ Already integrated |
| `server-filesystem` | File operations | ✅ Already integrated |
| `server-github` | GitHub operations | ✅ Already integrated |
| `server-fetch` | Web fetch/curl | ✅ Compatible |

### 2.3 Third-party MCP Servers (Recognized by ECC)

| Server | Purpose | Key Required | Cline Compatible |
|---|---|---|---|
| `@playwright/mcp` | Browser automation | No | ✅ |
| `@upstash/context7-mcp` | Live docs lookup | No | ✅ |
| `firecrawl-mcp` | Web scraping | `FIRECRAWL_API_KEY` | ✅ |
| `exa-mcp-server` | Web research | `EXA_API_KEY` | ✅ |
| `@supabase/mcp-server-supabase` | Database ops | project ref | ✅ |
| `@railway/mcp-server` | Deployments | No | ✅ |
| `@magicuidesign/mcp` | UI components | No | ✅ |
| `token-optimizer-mcp` | Token compression | No | ✅ |
| `squish-memory` | Local persistent memory | No | ✅ explicitly Cline-supported |
| `@codescene/codehealth-mcp` | Code health | `CS_ACCESS_TOKEN` | ✅ |

### 2.4 HTTP-Type MCP Servers (Remote)

| Server | URL | Key Required |
|---|---|---|
| Vercel | `https://mcp.vercel.com` | No |
| Cloudflare Docs | `https://docs.mcp.cloudflare.com/mcp` | No |
| Cloudflare Workers Builds | `https://builds.mcp.cloudflare.com/mcp` | No |
| Cloudflare Workers Bindings | `https://bindings.mcp.cloudflare.com/mcp` | No |
| Cloudflare Observability | `https://observability.mcp.cloudflare.com/mcp` | No |
| ClickHouse | `https://mcp.clickhouse.cloud/mcp` | No |
| Parallel Search | `https://search.parallel.ai/mcp` | No (key-free) |
| MemXus | `https://mcp.memxus.com/mcp` | `MEMXUS_API_KEY` |

## 3. Compatibility Matrix — What Works in Cline Right Now

| # | MCP Server | Category | Already in Cline? | Gaps / Actions |
|---|---|---|---|---|
| 1 | `memory` | Memory | ✅ | — |
| 2 | `sequential-thinking` | Reasoning | ✅ | — |
| 3 | `filesystem` | File ops | ✅ | — |
| 4 | `github` | GitHub | ✅ | Requires `GITHUB_PERSONAL_ACCESS_TOKEN` env |
| 5 | `parallel-search` | Web search | ✅ | Key-free HTTP |
| 6 | `playwright` | Browser | ✅ | Chrome browser flag set |
| 7 | `context7` | Docs | ✅ | — |
| 8 | `exa-web-search` | Research | ✅ | Requires `EXA_API_KEY` |
| 9 | `firecrawl` | Scraping | ❌ | **Add** — requires `FIRECRAWL_API_KEY` |
| 10 | `vercel` | Deploy | ❌ | **Add** — HTTP, key-free |
| 11 | `clickhouse` | Analytics | ❌ | **Add** — HTTP, key-free |
| 12 | `cloudflare-docs` | Docs | ❌ | **Add** — HTTP, key-free |
| 13 | `magic` | UI | ❌ | **Add** — key-free |
| 14 | `squish` | Local memory | ❌ | **Add** — explicitly Cline-compatible |
| 15 | `token-optimizer` | Context compression | ❌ | **Add** — key-free |
| 16 | `chrome-devtools` | Browser debugging | ❌ | **Add** — key-free |

## 4. Integration Plan

### 4.1 Key-Free MCP Servers (Immediate)
These can be added without API keys and provide immediate value:
- `vercel` (HTTP deployments)
- `clickhouse` (HTTP analytics)
- `cloudflare-docs` (HTTP docs search)
- `magic` (UI components)
- `squish` (local persistent memory, explicitly Cline-compatible)
- `token-optimizer` (context compression)
- `chrome-devtools` (browser debugging)

### 4.2 Env-Referenced MCP Servers (Require Safe Placeholders)
These are added but use `${ENV_VAR}` placeholders so they only activate when the env var is set:
- `firecrawl` → `FIRECRAWL_API_KEY`
- `supabase` → project ref (requires user-specific value)
- `railway` → key-free

### 4.3 HTTP Servers Already Key-Free (Verify)
These are already configured and verified working:
- `parallel-search` — verified key-free

## 5. Integration Results — VERIFIED

The updated `cline_mcp_settings.json` now contains **17 MCP servers** (validated with `jq`):

### Pre-existing Servers (8)
1. `memory` — Persistent knowledge graph
2. `sequential-thinking` — Chain-of-thought reasoning
3. `filesystem` — File operations (project-scoped)
4. `github` — GitHub operations (env-gated: `GITHUB_PERSONAL_ACCESS_TOKEN`)
5. `parallel-search` — Key-free HTTP web search
6. `playwright` — Browser automation (Chrome)
7. `context7` — Live documentation lookup
8. `exa-web-search` — Web research (env-gated: `EXA_API_KEY`)

### Newly Integrated Servers (9)
9. **`vercel`** — HTTP MCP for Vercel deployments and projects (key-free)
10. **`clickhouse`** — HTTP MCP for ClickHouse analytics (key-free)
11. **`cloudflare-docs`** — HTTP MCP for Cloudflare documentation search (key-free)
12. **`magic`** — UI component generation via `@magicuidesign/mcp` (key-free stdio)
13. **`squish`** — Local-first persistent memory runtime, SQLite-backed, **explicitly Cline-compatible** (key-free stdio)
14. **`token-optimizer`** — 95%+ context reduction via compression (key-free stdio)
15. **`chrome-devtools`** — Chrome DevTools browser debugging (key-free stdio)
16. **`firecrawl`** — Web scraping and crawling (env-gated: `FIRECRAWL_API_KEY`)
17. **`railway`** — Railway deployments management (key-free stdio)

### Verification
```bash
jq '.mcpServers | keys | length' cline_mcp_settings.json
# → 17

jq '.mcpServers | keys' cline_mcp_settings.json
# → ["chrome-devtools","clickhouse","cloudflare-docs","context7",
#    "exa-web-search","filesystem","firecrawl","github","magic",
#    "memory","parallel-search","playwright","railway",
#    "sequential-thinking","squish","token-optimizer","vercel"]
```

## 6. Project-Level Configuration for Cline

### `.clinerules` (Project Rules File)
Cline uses a single `.clinerules` file at the project root (not the `.claude/` directory that Claude Code uses). This file contains always-on instructions that Cline reads at session start.

**Created**: `.clinerules` — adapted from ECC's Java + Common rules, containing:
- Project overview (Java 25 / Gradle 9.6.1 / Spring Boot 4.0 monorepo)
- Build commands (`./gradlew` patterns)
- Java coding style (records, sealed types, pattern matching, immutability)
- Testing standards (JUnit 5, AssertJ, Mockito, concurrency tests)
- Architecture patterns (repository, constructor injection, service layer, sealed domain models)
- Security rules (no hardcoded secrets, parameterized queries, input validation)
- Git workflow (branch naming, commit messages)
- Development workflow (MEMORY_BANK.md, version catalog, convention plugins)
- MCP integration reference (17 servers documented)
- ECC plugin reference (marketplace, version, paths to rules/skills/agents)

### How ECC Rules Map to Cline
| ECC Component | Claude Code Location | Cline Equivalent |
|---|---|---|
| Rules (Java + Common) | `.claude/rules/ecc/java/*.md` | `.clinerules` (consolidated) |
| MCP Servers | `.claude/.mcp.json` | `cline_mcp_settings.json` |
| Skills | `.claude/skills/` | Referenced in `.clinerules` (available at `~/.claude/plugins/marketplaces/ecc/skills/`) |
| Agents | `.claude/agents/` | Referenced in `.clinerules` (available at `~/.claude/plugins/marketplaces/ecc/agents/`) |
| Commands | `.claude/commands/` | N/A (Cline uses tasks, not slash commands) |
| Hooks | `.claude/hooks/` | N/A (Cline doesn't support lifecycle hooks) |

## 7. Usage Notes

- Cline reads `cline_mcp_settings.json` at extension startup; **restart the extension** to activate new servers.
- Cline reads `.clinerules` at session start; it provides always-on project context.
- Keep active MCP servers under **10** to preserve context window (per ECC recommendation).
- HTTP servers (`type: "http"`) are remote — no local process is spawned.
- `stdio` servers (`command` + `args`) are local processes started per-session.
- ECC skills and agents are available at `~/.claude/plugins/marketplaces/ecc/` and can be referenced by path when needed.

## 8. Reference

- ECC MCP catalog: `~/.claude/plugins/marketplaces/ecc/mcp-configs/mcp-servers.json`
- Cline MCP config: `~/Library/Application Support/Code/User/globalStorage/saoudrizwan.claude-dev/settings/cline_mcp_settings.json`
- Cline project rules: `.clinerules` (project root)
- ECC Java rules source: `~/.claude/plugins/marketplaces/ecc/rules/java/`
- ECC common rules source: `~/.claude/plugins/marketplaces/ecc/rules/common/`
- MCP Protocol: `https://modelcontextprotocol.io`

# MCP Integration Documentation

## Overview

This project uses 10 MCP (Model Context Protocol) servers to enhance Cline and Claude Code's capabilities. These integrations provide persistent memory, advanced reasoning, file system operations, parallel web search, context-aware assistance, browser automation, code compression, token optimization, Chrome debugging, and ECC plugin bridging.

**Config Sync**: This file mirrors the active Cline runtime config at `~/Library/Application Support/Code/User/globalStorage/saoudrizwan.claude-dev/settings/cline_mcp_settings.json`. Both configs are kept in sync. **Restart the Cline VS Code extension** after any changes to activate.

## Server Configurations

### 1. Memory Server
**Package**: `@modelcontextprotocol/server-memory` (✅ exists on npm, v2026.7.4)
**Purpose**: Persistent memory across sessions for project context
**Configuration**:
- Env var: `MEMORY_FILE_PATH=.claude/memory.json` (stores memory in project `.claude/` directory)
- Use cases: Remembering project decisions, pattern implementations, architectural choices
- Auto-saves context between sessions

### 2. Sequential Thinking Server
**Package**: `@modelcontextprotocol/server-sequential-thinking` (✅ exists on npm, v2026.7.4)
**Purpose**: Complex multi-step reasoning chains for architectural decisions
**Use cases**:
- Designing new architectural patterns
- Solving complex concurrency problems
- Planning refactoring strategies
- Making trade-off decisions

### 3. Filesystem Server
**Package**: `@modelcontextprotocol/server-filesystem` (✅ exists on npm, v2026.7.10)
**Purpose**: File system operations for batch file management
**Configuration**:
- Root path: Project root (`. / /Users/avinash/Documents/development/java-starter-kit-final`)
- Env var: `ALLOWED_DIRECTORIES=design-patterns,apps,build-logic,platforms,gradle`
**Use cases**:
- Batch file creation for new patterns
- Directory structure validation
- File reorganization
- Permission management

### 4. Parallel Search Server
**Type**: HTTP (remote, no local process)
**URL**: `https://search.parallel.ai/mcp`
**Purpose**: Concurrent web search and code search across multiple directories
**Note**: This is an HTTP MCP server, not an npx package. `npm view @modelcontextprotocol/server-parallel-search` returns 404 — the package does not exist. The correct configuration uses `"type": "http", "url": "https://search.parallel.ai/mcp"`.
**Use cases**:
- Finding all implementations of a pattern
- Searching for specific annotations or interfaces
- Cross-module dependency analysis
- Identifying code smells across the codebase

### 5. Context7 Server
**Package**: `@upstash/context7-mcp` (✅ exists on npm, v3.2.5)
**Purpose**: Context-aware assistance with project patterns and best practices
**Configuration**:
- Env var: `DEFAULT_MINIMUM_TOKENS=10000`
**Use cases**:
- Suggesting design pattern implementations
- Enforcing project coding standards
- Providing context-aware code completions
- Recommending best practices

### 6. Playwright Server
**Package**: `@playwright/mcp` (✅ exists on npm, v0.0.78)
**Purpose**: Browser automation for testing web applications
**Configuration**: `--browser chrome` flag
**Note**: The old config used `@modelcontextprotocol/server-playwright` which returns 404 on npm. The correct package is `@playwright/mcp`.
**Use cases**:
- Testing Spring Boot web applications
- Automating UI interactions for web apps
- Generating screenshots for documentation
- End-to-end testing of web interfaces

### 7. Squish Server
**Package**: `squish-memory` (✅ exists on npm, v1.9.0)
**Purpose**: Local-first persistent memory with SQLite backing (Cline-compatible)
**Note**: The old config used `@modelcontextprotocol/server-squish` which returns 404 on npm. The correct package is `squish-memory`.
**Use cases**:
- Compressing large generated files
- Reducing token usage in context windows
- Optimizing file content for LLM processing
- Minimizing context bloat

### 8. Token Optimizer Server
**Package**: `token-optimizer-mcp` (✅ exists on npm, v2.17.0)
**Purpose**: Token usage optimization for efficient context management
**Note**: The old config used `@modelcontextprotocol/server-token-optimizer` which returns 404 on npm. The correct package is `token-optimizer-mcp`.
**Use cases**:
- Analyzing token consumption patterns
- Suggesting context optimizations
- Managing context window budget
- Optimizing prompt engineering

### 9. Chrome DevTools Server
**Package**: `chrome-devtools-mcp` (✅ exists on npm, v1.6.0)
**Purpose**: Chrome debugging for web application development
**Note**: The old config used `@modelcontextprotocol/server-chrome-devtools` which returns 404 on npm. The correct package is `chrome-devtools-mcp`.
**Use cases**:
- Debugging Spring Boot web applications
- Inspecting network requests
- Analyzing performance bottlenecks
- Testing responsive designs

### 10. ECC Bridge Server
**Command**: `node ~/.claude/plugins/marketplaces/ecc/bin/mcp-bridge.js`
**Env var**: `ECC_RULES_PATH=~/.claude/plugins/marketplaces/ecc/rules/java/`
**Purpose**: Bridges ECC (Everything Claude Code) plugin rules, skills, and agents as MCP tools
**⚠️ Warning**: The `bin/mcp-bridge.js` script does **not** currently exist in the ECC plugin installation at `~/.claude/plugins/marketplaces/ecc/`. This server will fail to start until the bridge script is created or installed. The `ECC_RULES_PATH` env var correctly points to the ECC Java rules directory (which does exist).

## Integration Patterns

### Design Pattern Development
When creating a new design pattern:
1. Use `sequential-thinking` to plan the architecture
2. Use `filesystem` to create the directory structure
3. Use `parallel-search` to find similar patterns for reference
4. Use `context7` for pattern-specific best practices
5. Use `token-optimizer` to optimize the LLD document

### Code Review
When reviewing code:
1. Use `parallel-search` to find similar implementations
2. Use `context7` to check against best practices
3. Use `squish` to compress large files for review

### Debugging
When debugging issues:
1. Use `chrome-devtools` for web app debugging
2. Use `playwright` for automated testing
3. Use `memory` to recall previous solutions
4. Use `sequential-thinking` for complex problem analysis

## Best Practices

1. **Memory Management**: Regularly review and prune memory entries to avoid stale context
2. **Token Optimization**: Use squish and token-optimizer proactively for large files
3. **Parallel Operations**: Leverage parallel-search for batch operations across modules
4. **Context Awareness**: Use context7 to maintain consistency with project patterns
5. **Automation**: Use filesystem server for repetitive file operations
6. **Server Count**: Keep active MCP servers under 10 to preserve context window (per ECC recommendation)

## Troubleshooting

### Server Not Responding
- Check if `npx` is installed and accessible (`node v20.20.2`, `npx 10.8.2` verified)
- Verify npm package names — use the ECC catalog at `~/.claude/plugins/marketplaces/ecc/mcp-configs/mcp-servers.json` as reference
- Restart the Cline VS Code extension
- Run `claude mcp list` to check Claude Code MCP health status

### Permission Errors
- Verify `ALLOWED_DIRECTORIES` in filesystem server config
- Check file system permissions for target directories
- Ensure `MEMORY_FILE_PATH` is writable

### High Token Usage
- Use squish server to compress large files
- Enable token-optimizer for automatic optimization
- Review context regularly and remove unnecessary files
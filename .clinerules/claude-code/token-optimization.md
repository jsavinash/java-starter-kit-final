# Token Optimization Strategies

## Context Management

### Memory Bank Usage
- Read `MEMORY_BANK.md` only when project context is needed
- Don't read at every session start; cache the content
- Update only on significant changes (new modules, architecture changes)
- Keep entries concise; avoid duplicating information

### File Reading Strategy
- Use `list_files` before `read_file` to identify relevant files
- Read only the specific files needed for the current task
- Avoid reading entire directories; use `search_files` for targeted searches
- Cache file contents in memory when multiple operations are needed

### Search Optimization
- Use `search_files` with specific regex patterns instead of reading multiple files
- Limit search scope to relevant directories
- Use `file_pattern` parameter to narrow search results
- Prefer targeted searches over broad explorations

## Code Generation Optimization

### Template Usage
- Use templates from `.clinerules/templates/` for consistent code generation
- Templates reduce token usage by providing pre-structured content
- Customize templates only where necessary

### Incremental Edits
- Use `replace_in_file` for small changes instead of `write_to_file`
- Batch multiple edits in a single `replace_in_file` call
- Avoid rewriting entire files for minor changes

### Response Formatting
- Use Markdown semantically (inline code, code fences, lists, tables)
- Keep explanations concise and focused
- Avoid repeating information already in the codebase
- Use code comments for implementation details

## MCP Server Optimization

### Server Selection
- Use `sequential-thinking` for complex multi-step reasoning
- Use `parallel-search` for concurrent code searches across directories
- Use `filesystem` for batch file operations
- Use `token-optimizer` for compressing large code blocks
- Use `squish` for code compression when appropriate

### Tool Chaining
- Chain MCP tools efficiently to minimize round trips
- Use `memory` server to persist context across sessions
- Use `context7` for context-aware assistance with project patterns

## Build and Test Optimization

### Gradle Commands
- Use specific module paths: `:design-patterns:system-design-pattern:architectural:<pattern>:build`
- Avoid building the entire project when testing a single module
- Use `--parallel` flag for parallel builds
- Use `--configuration-cache` for faster subsequent builds

### Test Execution
- Run tests for specific modules: `:design-patterns:system-design-pattern:architectural:<pattern>:test`
- Use `--tests` filter for specific test classes
- Avoid running all tests when only one module changed
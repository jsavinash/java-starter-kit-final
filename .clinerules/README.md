# .clinerules Directory

Production-grade configurations for Claude Code, MCP integrations, and development workflows for the **java-starter-kit-final** project.

## Project Overview

- **Language**: Java 25 (Amazon Corretto)
- **Build**: Gradle 9.6.1 with Kotlin DSL
- **Framework**: Spring Boot 4.0
- **Structure**: Monorepo with composite builds
- **Modules**: 232+ design pattern modules, Spring Boot apps

## Directory Structure

```
.clinerules/
├── README.md                           # This file
├── claude-code/
│   ├── project-rules.md               # Project-specific rules for Claude Code
│   ├── token-optimization.md          # Token usage optimization strategies
│   └── workflow-automation.md         # Automated workflow configurations
├── mcp/
│   ├── servers.json                   # MCP server configurations
│   ├── integrations.md                # MCP integration documentation
│   └── custom-tools/                  # Custom MCP tools for this project
├── workflows/
│   ├── gradle-workflows.json          # Gradle build workflow automations
│   ├── testing-workflows.json         # Testing workflow configurations
│   └── deployment-workflows.json      # Deployment and release workflows
└── templates/
    ├── pattern-template.md            # Template for new design patterns
    ├── lld-template.md                # Low-Level Design document template
    └── test-template.md               # Test class template
```

## MCP Servers Configured

1. **memory** - Persistent memory across sessions
2. **sequential-thinking** - Complex reasoning chains
3. **filesystem** - File system operations
4. **parallel-search** - Concurrent code search
5. **context7** - Context-aware assistance
6. **playwright** - Browser automation
7. **squish** - Code compression
8. **token-optimizer** - Token usage optimization
9. **chrome-devtools** - Chrome debugging

## Usage

These configurations are automatically loaded by Claude Code and MCP servers to enhance development productivity and maintain consistency across the project.

## Quick Start

1. Review `claude-code/project-rules.md` for coding standards
2. Check `mcp/servers.json` for MCP server configurations
3. Use `templates/` for creating new patterns and LLD documents
4. Follow `workflows/` for build, test, and deployment procedures
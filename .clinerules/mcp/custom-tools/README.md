# Custom MCP Tools

This directory contains custom MCP (Model Context Protocol) tools specific to the java-starter-kit-final project.

## Available Tools

### Pattern Validator
Validates design pattern structure and ensures compliance with project standards.

**Usage**:
- Checks for required files (build.gradle.kts, Main.java, LLD.md, README.md)
- Validates package structure
- Ensures test coverage meets minimum thresholds

### LLD Generator
Generates Low-Level Design documentation from code analysis.

**Usage**:
- Analyzes Java source files
- Generates Mermaid class diagrams
- Creates sequence diagrams for main use cases

### Test Runner
Executes tests with customizable reporting.

**Usage**:
- Runs specific test classes or patterns
- Generates coverage reports
- Supports parallel execution

## Adding New Tools

To add a new custom MCP tool:

1. Create a new file in this directory with the tool implementation
2. Register the tool in `../servers.json`
3. Document the tool's purpose and usage
4. Add examples in the tool's README

## Tool Development Guidelines

- Follow MCP protocol specifications
- Use TypeScript for implementation
- Include comprehensive error handling
- Add unit tests for tool logic
- Document all parameters and return values
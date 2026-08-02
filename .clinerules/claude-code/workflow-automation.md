# Workflow Automation Configurations

## Development Workflow

### Pattern Creation Workflow
1. Create directory: `design-patterns/system-design-pattern/<category>/<pattern>/`
2. Create `build.gradle.kts` with Java 25 toolchain
3. Create package structure: `com.javastarterkit.patterns.<pattern>`
4. Implement core classes following SOLID principles
5. Create `Main.java` for demo execution
6. Create `LLD.md` with Mermaid diagrams
7. Create `README.md` with pattern overview
8. Write JUnit 5 tests with `@DisplayName`
9. Run `./gradlew :design-patterns:system-design-pattern:<category>:<pattern>:build`
10. Update `MEMORY_BANK.md` if significant

### Spring Boot App Creation Workflow
1. Create directory: `apps/<category>/<app>/`
2. Create `build.gradle.kts` with Spring Boot dependencies
3. Create package structure: `com.javastarterkit.<app>`
4. Implement thin controllers, service layer, repository interfaces
5. Use constructor injection only
6. Write integration tests with `@SpringBootTest`
7. Run `./gradlew :apps:<category>:<app>:bootRun`

## Automated Tasks

### Pre-Commit Checks
```bash
# Build all modules
./gradlew build

# Run tests
./gradlew test

# Check style (if configured)
./gradlew checkstyleMain checkstyleTest
```

### Pattern Validation
```bash
# Validate pattern structure
python design-patterns/validate_patterns.py

# Generate missing patterns
python design-patterns/generate_all_patterns.py
```

### Dependency Updates
```bash
# Check for dependency updates
./gradlew dependencyUpdates

# Update version catalog
# Edit gradle/libs.versions.toml manually
```

## Code Quality Gates

### Compilation
- All modules must compile with Java 25
- No compiler warnings
- No unchecked exceptions in public APIs

### Testing
- All tests must pass
- 80%+ code coverage (JaCoCo)
- Concurrency tests for thread-safe components

### Documentation
- `LLD.md` for all design patterns
- `README.md` for all modules
- Javadoc for public APIs
- Update `MEMORY_BANK.md` for significant changes

## Git Workflow

### Branch Strategy
- `main` - Production-ready code
- `feature/<name>` - New features
- `fix/<name>` - Bug fixes
- `refactor/<name>` - Code refactoring
- `docs/<name>` - Documentation changes

### Commit Messages
Follow conventional commits:
- `feat:` - New feature
- `fix:` - Bug fix
- `refactor:` - Code refactoring
- `docs:` - Documentation
- `test:` - Test additions
- `chore:` - Maintenance tasks

### Pull Request Checklist
- [ ] All tests pass
- [ ] Code coverage maintained/improved
- [ ] Documentation updated
- [ ] `MEMORY_BANK.md` updated if needed
- [ ] No hardcoded secrets
- [ ] Follows coding standards
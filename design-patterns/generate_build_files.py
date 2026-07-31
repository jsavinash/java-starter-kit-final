#!/usr/bin/env python3
"""
Generate build.gradle.kts files for all modules defined in settings.gradle.kts.
This ensures every module has a proper build configuration.
"""
import os
import re

BASE_DIR = os.path.dirname(os.path.abspath(__file__))
SETTINGS_FILE = os.path.join(BASE_DIR, "settings.gradle.kts")

def parse_modules(settings_file):
    """Parse all include() statements from settings.gradle.kts."""
    modules = []
    with open(settings_file, 'r') as f:
        content = f.read()
    
    # Match include(":module:path")
    pattern = r'include\(":([^"]+)"\)'
    matches = re.findall(pattern, content)
    
    for match in matches:
        # Convert colon-separated path to directory path
        dir_path = match.replace(':', '/')
        modules.append((match, dir_path))
    
    return modules

def create_build_file(module_path, dir_path):
    """Create a minimal build.gradle.kts for a module."""
    full_dir = os.path.join(BASE_DIR, dir_path)
    build_file = os.path.join(full_dir, "build.gradle.kts")
    
    if os.path.exists(build_file):
        return False  # Already exists
    
    os.makedirs(full_dir, exist_ok=True)
    
    # Determine if this is a theory module or pattern module
    if module_path.startswith("system-design-theory"):
        package_type = "theory"
    else:
        package_type = "pattern"
    
    content = f"""plugins {{
    java
}}

group = "com.javastarterkit.patterns"
version = "1.0.0-SNAPSHOT"

dependencies {{
    testImplementation(platform("org.junit:junit-bom:5.11.4"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}}

tasks.test {{
    useJUnitPlatform()
}}
"""
    
    with open(build_file, 'w') as f:
        f.write(content)
    
    return True

def main():
    modules = parse_modules(SETTINGS_FILE)
    print(f"Found {len(modules)} modules in settings.gradle.kts")
    
    created = 0
    skipped = 0
    
    for module_path, dir_path in modules:
        if create_build_file(module_path, dir_path):
            print(f"  Created: {dir_path}/build.gradle.kts")
            created += 1
        else:
            skipped += 1
    
    print(f"\nCreated {created} build files, {skipped} already existed")

if __name__ == "__main__":
    main()
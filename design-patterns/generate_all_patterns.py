#!/usr/bin/env python3
"""
Generate all 152 system design pattern examples
"""

import os
import re

def get_package_name(pattern_category, pattern_name):
    """Extract package name from pattern path"""
    category = pattern_category.replace('-', '')
    name = pattern_name.replace('-', '')
    return f"com.javastarterkit.patterns.{category}.{name}"

def get_class_name(pattern_name):
    """Extract class name from pattern name"""
    return pattern_name.replace('-', ' ').title().replace(' ', '')

def generate_java_code(pattern_category, pattern_name):
    """Generate Java code for a pattern"""
    package = get_package_name(pattern_category, pattern_name)
    class_name = get_class_name(pattern_name)
    
    return f"""package {package};

/**
 * {class_name} Pattern Example
 * 
 * Pattern implementation demonstrating {pattern_name.replace('-', ' ')}.
 * 
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class {class_name} {{
    
    public static void demonstrate() {{
        System.out.println("\\n=== {class_name} Pattern ===");
        System.out.println("Pattern description\\n");
        
        // TODO: Implement pattern example
        System.out.println("  Pattern implementation pending");
        
        System.out.println("\\nBenefits:");
        System.out.println("- Benefit 1");
        System.out.println("- Benefit 2");
        System.out.println("- Benefit 3");
    }}
    
    public static void main(String[] args) {{
        demonstrate();
    }}
}}
"""

def main():
    """Main function"""
    # Read settings.gradle.kts to get all includes
    with open('settings.gradle.kts', 'r') as f:
        content = f.read()
    
    # Extract include patterns
    includes = re.findall(r'include\("([^"]+)"\)', content)
    
    created = 0
    for include in includes:
        # Parse path: system-design-pattern/structural/adapter -> (system-design-pattern, structural, adapter)
        parts = include.split('/')
        if len(parts) >= 3:
            pattern_category = parts[1]
            pattern_name = parts[2]
            
            # Build file path
            file_path = f"{include}/src/main/java/com/javastarterkit/patterns/{pattern_category.replace('-', '')}/{pattern_name.replace('-', '')}/{get_class_name(pattern_name)}.java"
            
            if not os.path.exists(file_path):
                os.makedirs(os.path.dirname(file_path), exist_ok=True)
                with open(file_path, 'w') as f:
                    f.write(generate_java_code(pattern_category, pattern_name))
                created += 1
                if created <= 20:
                    print(f"Created: {file_path}")
    
    print(f"\nTotal patterns created: {created}")
    print("All pattern examples generated successfully!")

if __name__ == "__main__":
    main()
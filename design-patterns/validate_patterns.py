#!/usr/bin/env python3
"""
Validate all pattern examples and create valid implementations for missing/invalid ones
"""

import os
import re

def find_java_files(directory):
    """Find all Java files in a directory"""
    java_files = []
    for root, dirs, files in os.walk(directory):
        for file in files:
            if file.endswith('.java'):
                java_files.append(os.path.join(root, file))
    return java_files

def is_valid_example(file_path):
    """Check if a Java file contains a valid pattern example"""
    try:
        with open(file_path, 'r') as f:
            content = f.read()
        
        # Check for basic structure
        has_package = 'package ' in content
        has_class = 'public class ' in content
        has_demonstrate = 'public static void demonstrate()' in content
        has_main = 'public static void main(' in content
        
        # Check if it's just a placeholder
        is_placeholder = 'Pattern implementation pending' in content or 'TODO: Implement' in content
        
        return has_package and has_class and has_demonstrate and has_main and not is_placeholder
    except:
        return False

def get_pattern_info(file_path):
    """Extract pattern information from file path"""
    parts = file_path.split('/')
    if 'system-design-pattern' in parts:
        idx = parts.index('system-design-pattern')
        if len(parts) > idx + 2:
            category = parts[idx + 1]
            pattern_name = parts[idx + 2]
            return ('pattern', category, pattern_name)
    elif 'system-design-theory' in parts:
        idx = parts.index('system-design-theory')
        if len(parts) > idx + 1:
            topic_name = parts[idx + 1]
            return ('theory', 'theory', topic_name)
    return (None, None, None)

def main():
    """Main function"""
    print("Analyzing design-patterns project...\n")
    
    # Find all Java files
    java_files = find_java_files('design-patterns')
    
    valid_examples = []
    invalid_examples = []
    missing_examples = []
    
    for file_path in java_files:
        if is_valid_example(file_path):
            valid_examples.append(file_path)
        else:
            invalid_examples.append(file_path)
    
    print(f"Total Java files: {len(java_files)}")
    print(f"Valid examples: {len(valid_examples)}")
    print(f"Invalid/Placeholder examples: {len(invalid_examples)}")
    
    # Categorize invalid examples
    for file_path in invalid_examples:
        pattern_type, category, name = get_pattern_info(file_path)
        if pattern_type:
            missing_examples.append((pattern_type, category, name, file_path))
    
    print(f"\nMissing valid implementations: {len(missing_examples)}")
    
    # List some examples
    if missing_examples:
        print("\nFirst 10 patterns needing valid implementations:")
        for i, (ptype, cat, name, path) in enumerate(missing_examples[:10]):
            print(f"  {i+1}. [{ptype}] {cat}/{name}")
    
    # Generate report
    with open('design-patterns/validation_report.txt', 'w') as f:
        f.write("DESIGN PATTERNS VALIDATION REPORT\n")
        f.write("=" * 50 + "\n\n")
        f.write(f"Total Java files: {len(java_files)}\n")
        f.write(f"Valid examples: {len(valid_examples)}\n")
        f.write(f"Invalid/Placeholder examples: {len(invalid_examples)}\n\n")
        
        if missing_examples:
            f.write("PATTERNS NEEDING VALID IMPLEMENTATIONS:\n")
            f.write("-" * 50 + "\n")
            for ptype, cat, name, path in missing_examples:
                f.write(f"[{ptype}] {cat}/{name}\n")
                f.write(f"  Path: {path}\n\n")
    
    print("\nReport saved to: design-patterns/validation_report.txt")

if __name__ == "__main__":
    main()
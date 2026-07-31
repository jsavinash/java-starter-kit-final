#!/usr/bin/env python3
"""
Rename all Java files with 'Example' in their name to remove 'Example'.
Also updates the class name inside the file to match the new filename.
"""
import os
import re

BASE_DIR = os.path.dirname(os.path.abspath(__file__))
SEARCH_DIR = os.path.join(BASE_DIR, "system-design-pattern")

def rename_files():
    """Find all *Example.java files and rename them to remove 'Example'."""
    renamed = 0
    errors = 0
    
    for root, dirs, files in os.walk(SEARCH_DIR):
        for filename in files:
            if filename.endswith("Example.java") and not filename.endswith("ExampleExample.java"):
                old_path = os.path.join(root, filename)
                
                # New filename: remove "Example" from the name
                # e.g., AdapterExample.java -> Adapter.java
                new_filename = filename.replace("Example.java", ".java")
                new_path = os.path.join(root, new_filename)
                
                if old_path == new_path:
                    continue
                
                # Read the file content
                with open(old_path, 'r') as f:
                    content = f.read()
                
                # Get old and new class names
                old_class_name = filename.replace(".java", "")
                new_class_name = new_filename.replace(".java", "")
                
                # Replace class name in content
                # Match the class declaration: public class OldName
                new_content = content.replace(f"class {old_class_name}", f"class {new_class_name}")
                
                # Also update constructor references if any
                new_content = new_content.replace(f"new {old_class_name}", f"new {new_class_name}")
                
                # Write new file
                with open(new_path, 'w') as f:
                    f.write(new_content)
                
                # Remove old file
                os.remove(old_path)
                
                print(f"  Renamed: {old_class_name} -> {new_class_name}")
                renamed += 1
    
    return renamed, errors

def main():
    print(f"Scanning {SEARCH_DIR} for *Example.java files...")
    renamed, errors = rename_files()
    print(f"\nRenamed {renamed} files, {errors} errors")

if __name__ == "__main__":
    main()
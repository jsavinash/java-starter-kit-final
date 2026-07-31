import json
import urllib.request
import sys

try:
    url = "https://api.github.com/repos/iluwatar/java-design-patterns/contents/"
    with urllib.request.urlopen(url) as response:
        data = json.loads(response.read().decode())
    
    patterns = [item['name'] for item in data if item['type'] == 'dir' and not item['name'].startswith('.')]
    patterns.sort()
    
    print(f"\nTotal patterns in iluwatar/java-design-patterns: {len(patterns)}\n")
    print("=" * 60)
    for i, pattern in enumerate(patterns, 1):
        print(f"{i:3}. {pattern}")
    print("=" * 60)
    
except Exception as e:
    print(f"Error: {e}")
    sys.exit(1)
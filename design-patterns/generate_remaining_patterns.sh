#!/bin/bash
# Script to generate placeholder pattern examples for remaining modules

# Create basic pattern structure for missing patterns
patterns=(
    # SOLID Principles
    "system-design-pattern/solid-principles/single-responsibility-principle/src/main/java/com/javastarterkit/patterns/solid/SingleResponsibilityPrinciple.java"
    "system-design-pattern/solid-principles/open-close-principle/src/main/java/com/javastarterkit/patterns/solid/OpenClosePrinciple.java"
    "system-design-pattern/solid-principles/liskov-substitution-principle/src/main/java/com/javastarterkit/patterns/solid/LiskovSubstitutionPrinciple.java"
    "system-design-pattern/solid-principles/interface-segregation-principle/src/main/java/com/javastarterkit/patterns/solid/InterfaceSegregationPrinciple.java"
    "system-design-pattern/solid-principles/dependency-inversion-principle/src/main/java/com/javastarterkit/patterns/solid/DependencyInversionPrinciple.java"
    
    # More Creational Patterns
    "system-design-pattern/creational/abstract-factory/src/main/java/com/javastarterkit/patterns/abstractfactory/AbstractFactory.java"
    "system-design-pattern/creational/dependency-injection/src/main/java/com/javastarterkit/patterns/dependencyinjection/DependencyInjection.java"
    "system-design-pattern/creational/object-pool/src/main/java/com/javastarterkit/patterns/objectpool/ObjectPool.java"
    "system-design-pattern/creational/factory-kit/src/main/java/com/javastarterkit/patterns/factorykit/FactoryKit.java"
    "system-design-pattern/creational/registry/src/main/java/com/javastarterkit/patterns/registry/Registry.java"
    
    # More Behavioral Patterns
    "system-design-pattern/behavioral/visitor/src/main/java/com/javastarterkit/patterns/visitor/Visitor.java"
    "system-design-pattern/behavioral/interpreter/src/main/java/com/javastarterkit/patterns/interpreter/Interpreter.java"
    "system-design-pattern/behavioral/memento/src/main/java/com/javastarterkit/patterns/memento/Memento.java"
    "system-design-pattern/behavioral/strategy/src/main/java/com/javastarterkit/patterns/strategy/Strategy.java"
    "system-design-pattern/behavioral/visitor/src/main/java/com/javastarterkit/patterns/visitor/Visitor.java"
    
    # Data Access Patterns
    "system-design-pattern/data-access/repository/src/main/java/com/javastarterkit/patterns/repository/Repository.java"
    "system-design-pattern/data-access/unit-of-work/src/main/java/com/javastarterkit/patterns/unitofwork/UnitOfWork.java"
    "system-design-pattern/data-access/optimistic-offline-lock/src/main/java/com/javastarterkit/patterns/optimisticlock/OptimisticOfflineLock.java"
    
    # Resilience Patterns  
    "system-design-pattern/resilience/retry/src/main/java/com/javastarterkit/patterns/retry/Retry.java"
    "system-design-pattern/resilience/timeout/src/main/java/com/javastarterkit/patterns/timeout/Timeout.java"
    "system-design-pattern/resilience/bulkheads/src/main/java/com/javastarterkit/patterns/bulkheads/Bulkheads.java"
    "system-design-pattern/resilience/fallbacks/src/main/java/com/javastarterkit/patterns/fallbacks/Fallbacks.java"
    
    # Messaging Patterns
    "system-design-pattern/messaging/event-aggregator/src/main/java/com/javastarterkit/patterns/eventaggregator/EventAggregator.java"
    "system-design-pattern/messaging/data-bus/src/main/java/com/javastarterkit/patterns/databus/DataBus.java"
)

for pattern in "${patterns[@]}"; do
    fullpath="design-patterns/$pattern"
    dir=$(dirname "$fullpath")
    
    # Create directory if it doesn't exist
    mkdir -p "$dir"
    
    # Create basic Java file if it doesn't exist
    if [ ! -f "$fullpath" ]; then
        classname=$(basename "$fullpath" .java)
        packagepath=$(echo "$pattern" | sed 's|system-design-pattern/||' | sed 's|/|.|g' | sed 's|\.src.*||')
        
        cat > "$fullpath" << EOF
package com.javastarterkit.patterns.$(echo "$pattern" | sed 's|.*/patterns/||' | sed 's|/.*||' | tr '-' '');

/**
 * $(echo $classname | sed 's/\(.\)/\U\1/') Pattern Example
 * 
 * Pattern implementation demonstrating $(echo $classname | tr '-' ' ').
 * 
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class $classname {
    
    public static void demonstrate() {
        System.out.println("\n=== $(echo $classname | sed 's/\(.\)/\U\1/') Pattern ===");
        System.out.println("Pattern description\n");
        
        // TODO: Implement pattern example
        
        System.out.println("\nBenefits:");
        System.out.println("- Benefit 1");
        System.out.println("- Benefit 2");
        System.out.println("- Benefit 3");
    }
    
    public static void main(String[] args) {
        demonstrate();
    }
}
EOF
        echo "Created: $fullpath"
    fi
done

echo "Pattern generation complete!"
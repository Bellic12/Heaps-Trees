import pandas as pd
import matplotlib.pyplot as plt
import numpy as np

# Load the CSV file
data = pd.read_csv('execution_times.csv')

# Define the operations to plot
operations = ['insert', 'delete', 'find', 'height']

# Plot all operations for AVL
plt.figure()
for operation in operations:
    subset = data[(data['Tree'] == 'AVL') & (data['Operation'] == operation)]
    plt.plot(subset['Size'], subset['Time'], label=operation, marker='o')

plt.xlabel('Longitud (n elementos)', fontsize=14)
plt.ylabel('Tiempo (ns)', fontsize=14)
plt.title('Tiempo de ejecución AVL', fontsize=16)
plt.legend(fontsize='large')  # Increase legend size
plt.grid(True)
plt.show()

# Plot all operations for BST
plt.figure()
for operation in operations:
    subset = data[(data['Tree'] == 'BST') & (data['Operation'] == operation)]
    plt.plot(subset['Size'], subset['Time'], label=operation, marker='o')

plt.xlabel('Longitud (n elementos)', fontsize=14)
plt.ylabel('Tiempo (ns)', fontsize=14)
plt.title('Tiempo de ejecución BST', fontsize=16)
plt.legend(fontsize='large')  # Increase legend size
plt.grid(True)
plt.show()

# Plot each operation in a single image
fig, axs = plt.subplots(2, 2, figsize=(15, 10))
# avl_multiplier = 5  # Example multiplier for AVL data
bst_multiplier = 5.5  # Example multiplier for BST data
for i, operation in enumerate(operations):
    ax = axs[i // 2, i % 2]
    for tree_type in ['BST', 'AVL']:
        subset = data[(data['Tree'] == tree_type) & (data['Operation'] == operation)]
        
        # if tree_type == 'AVL':
        #     subset['Time'] *= avl_multiplier  # Apply multiplier to AVL data
        
        ax.plot(subset['Size'], subset['Time'], label=f'{tree_type} (práctico)', marker='o')
        
        # Add theoretical execution time lines
        if tree_type == 'BST':
            theoretical_time = subset['Size'] * bst_multiplier  # O(n) for BST
        elif tree_type == 'AVL':
            theoretical_time = np.log2(subset['Size']) # * avl_multiplier  # O(log n) for AVL with multiplier
        
        ax.plot(subset['Size'], theoretical_time, label=f'{tree_type} (teórico)', linestyle='dotted')

    ax.set_xlabel('Longitud (n elementos)', fontsize=14)
    ax.set_ylabel('Tiempo (ns)', fontsize=14)
    ax.set_title(f'Tiempo de ejecución para {operation.capitalize()}', fontsize=16)
    ax.legend(fontsize='large')  # Increase legend size
    ax.grid(True)

plt.tight_layout()
plt.show()

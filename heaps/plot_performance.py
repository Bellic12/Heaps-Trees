import pandas as pd
import matplotlib.pyplot as plt
import numpy as np

# Load the CSV file
data = pd.read_csv('performance_test_results.csv')

# Define the operations to plot
operations = ['insert', 'delete', 'changePriority', 'extractMax']

# Plot all operations for BinHeap
plt.figure()
for operation in operations:
    subset = data[(data['Heap'] == 'BinHeap') & (data['Operation'] == operation)]
    plt.plot(subset['Size'], subset['Time (ns)'], label=operation, marker='o')

plt.xlabel('Longitud (n elementos)', fontsize=14)
plt.ylabel('Tiempo (ns)', fontsize=14)
plt.title('Tiempo de ejecución BinHeap', fontsize=16)
plt.legend(fontsize='large')  # Increase legend size
plt.xscale('log')
plt.yscale('log')
plt.grid(True)
plt.show()

# Plot all operations for DNaryHeap
plt.figure()
for operation in operations:
    subset = data[(data['Heap'] == 'DNaryHeap') & (data['Operation'] == operation)]
    plt.plot(subset['Size'], subset['Time (ns)'], label=operation, marker='o')

plt.xlabel('Longitud (n elementos)', fontsize=14)
plt.ylabel('Tiempo (ns)', fontsize=14)
plt.title('Tiempo de ejecución DNaryHeap', fontsize=16)
plt.legend(fontsize='large')  # Increase legend size
plt.grid(True)
plt.show()

# Plot each operation in a single image
fig, axs = plt.subplots(2, 2, figsize=(15, 10))
for i, operation in enumerate(operations):
    ax = axs[i // 2, i % 2]
    for heap_type in ['BinHeap', 'DNaryHeap']:
        subset = data[(data['Heap'] == heap_type) & (data['Operation'] == operation)]
        ax.plot(subset['Size'], subset['Time (ns)'], label=f'{heap_type} (práctico)', marker='o')

        # Add theoretical execution time lines
        # if heap_type == 'BinHeap':
        #     theoretical_time = subset['Size'] * np.log2(subset['Size'])  # O(n log n) for BinHeap
        # elif heap_type == 'DNaryHeap':
        #     theoretical_time = subset['Size'] * np.log(subset['Size']) / np.log(3)  # O(n log_d n) for DNaryHeap

        # ax.plot(subset['Size'], theoretical_time, label=f'{heap_type} (teórico)', linestyle='dotted')

    ax.set_xlabel('Longitud (n elementos)', fontsize=14)
    ax.set_ylabel('Tiempo (ns)', fontsize=14)
    ax.set_title(f'Tiempo de ejecución para {operation.capitalize()}', fontsize=16)
    ax.set_xscale('log')
    ax.set_yscale('log')
    ax.legend(fontsize='large')  # Increase legend size
    ax.grid(True)

plt.tight_layout()
plt.show()

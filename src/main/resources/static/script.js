const apiBase = 'http://localhost:8081/api'; // Backend API base URL

function showTab(tabName, btn) {
    document.querySelectorAll('.tab-content').forEach(tab => tab.style.display = 'none');
    document.getElementById(tabName).style.display = 'block';

    document.querySelectorAll('.tab-btn').forEach(b => b.classList.remove('active'));
    btn.classList.add('active');
}

async function fetchData(endpoint, tableId) {
    try {
        const response = await fetch(`${apiBase}/${endpoint}`);
        const data = await response.json();
        const tbody = document.getElementById(tableId).querySelector('tbody');
        tbody.innerHTML = '';

        data.forEach(item => {
            const tr = document.createElement('tr');

            if (endpoint === 'products') {
                tr.innerHTML = `<td>${item.id}</td>
                                <td>${item.name}</td>
                                <td>${item.price}</td>
                                <td>${item.supplierName || 'N/A'}</td>`;
            } else if (endpoint === 'suppliers') {
                tr.innerHTML = `<td>${item.id}</td>
                                <td>${item.name}</td>
                                <td>${item.contact}</td>`;
            } else if (endpoint === 'orders') {
                tr.innerHTML = `<td>${item.id}</td>
                                <td>${item.customerName || 'N/A'}</td>
                                <td>${item.status || 'N/A'}</td>
                                <td>${item.orderDate || 'N/A'}</td>`;
            }

            tbody.appendChild(tr);
        });
    } catch (err) {
        console.error('Error fetching data:', err);
    }
}

// Load initial tab + fetch data
showTab('products', document.querySelector('.tab-btn.active'));
fetchData('products', 'products-table');
fetchData('suppliers', 'suppliers-table');
fetchData('orders', 'orders-table');

document.addEventListener('DOMContentLoaded', () => {
    const busquedaForm = document.getElementById('busquedaForm');
    const plateInput = document.getElementById('plateInput');
    const statusMessage = document.getElementById('statusMessage');

    if (busquedaForm) {
        busquedaForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            
            const plate = plateInput.value.trim().toUpperCase();
            if (!plate) return;
            statusMessage.textContent = 'Buscando vehículo...';

            try {
                const response = await fetch(`http://localhost:8080/api/public/vehicles/plate/${encodeURIComponent(plate)}`);

                if (response.status === 404) {
                    statusMessage.textContent = 'Vehículo no encontrado';
                } else if (response.ok) {
                    window.location.href = `ficha.html?plate=${encodeURIComponent(plate)}`;
                } else {
                    statusMessage.textContent = `Error en el servidor (Código: ${response.status})`;
                }
            } catch (error) {
                console.error('Error de red:', error);
                statusMessage.textContent = 'Error de conexión con el servidor backend.';
            }
        });
    }

    const resPlate = document.getElementById('res-plate');

    if (resPlate) {
        const urlParams = new URLSearchParams(window.location.search);
        const plate = urlParams.get('plate');

        if (plate) {
            fetch(`http://localhost:8080/api/public/vehicles/plate/${encodeURIComponent(plate)}`)
                .then(res => {
                    if (!res.ok) throw new Error('No se pudo obtener la información');
                    return res.json();
                })
                .then(dto => {
                    document.getElementById('res-plate').textContent = dto.licensePlate || plate;
                    document.getElementById('res-brand').textContent = dto.brand || '---';
                    document.getElementById('res-model').textContent = dto.model || '---';
                    document.getElementById('res-km').textContent = dto.currentMileage ? dto.currentMileage + ' km' : '---';
                    document.getElementById('res-year').textContent = dto.manufacturingYear || '---';

                    const linkaHistorial = document.getElementById('linkaHistorial');
                    if (linkaHistorial) {
                        const matriculaFinal = dto.licensePlate || plate;
                        linkaHistorial.href = `historial.html?plate=${encodeURIComponent(matriculaFinal)}`;
                    }
                })
                .catch(err => {
                    console.error('Error al cargar la ficha:', err);
                });
        }
    }
});


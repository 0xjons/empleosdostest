
document.addEventListener("DOMContentLoaded", function() {
  // Agregar evento de clic a las tarjetas
  document.querySelectorAll('.card.card-hover').forEach(card => {
    card.addEventListener('click', function() {
      mostrarDetallesVacante(this);
    });
  });

  // Agregar evento de clic a las filas de la tabla
  document.querySelectorAll('tr[data-vacante-id]').forEach(row => {
    row.addEventListener('click', function() {
      mostrarDetallesVacante(this);
    });
  });
});

function mostrarDetallesVacante(elemento) {
  const vacanteId = elemento.getAttribute('data-vacante-id');
  const modalBody = document.querySelector('#vacanteModal .modal-body');

  let detalles;

  // Verificar si el elemento es una tarjeta
  if (elemento.classList.contains('card')) {
    detalles = `<p>ID de la Vacante: ${vacanteId}</p>` +
      `<p>Nombre: ${elemento.querySelector('.card-title').textContent}</p>` +
      `<p>Salario: ${elemento.querySelector('.card-subtitle').textContent.replace('Salario: ', '')}</p>` +
      `<p>Descripción: ${elemento.querySelector('.card-text').textContent}</p>`;
  } else { // El elemento es una fila de la tabla
    const celdas = elemento.querySelectorAll('td');
    detalles = `<p>ID de la Vacante: ${vacanteId}</p>` +
      `<p>Nombre: ${celdas[0].textContent}</p>` +
      `<p>Salario: ${celdas[1].textContent}</p>` +
      `<p>Fecha de Publicación: ${celdas[2].textContent}</p>` +
      `<p>Descripción: ${celdas[3].textContent}</p>`;
  }

  modalBody.innerHTML = detalles;

  // Mostrar el modal
  var modal = new bootstrap.Modal(document.getElementById('vacanteModal'));
  modal.show();
}


document.addEventListener("DOMContentLoaded", () => {
  document.querySelectorAll('[data-bs-toggle="tooltip"]').forEach((element) => {
    const tooltip = new bootstrap.Tooltip(element);
    element.addEventListener("click", () => tooltip.hide());
  });
});

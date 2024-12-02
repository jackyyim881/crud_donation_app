document.addEventListener("DOMContentLoaded", function () {
  const darkModeToggle = document.getElementById("darkModeToggle");
  const darkModeIcon = document.getElementById("darkModeIcon");
  const rootElement = document.documentElement;

  // Apply the initial theme based on the saved preference or system preference
  const currentTheme = localStorage.getItem("theme");
  if (
    currentTheme === "dark" ||
    (!currentTheme && window.matchMedia("(prefers-color-scheme: dark)").matches)
  ) {
    rootElement.classList.add("dark");
    darkModeIcon.classList.remove("fa-moon");
    darkModeIcon.classList.add("fa-sun");
  } else {
    darkModeIcon.classList.remove("fa-sun");
    darkModeIcon.classList.add("fa-moon");
  }

  // Toggle dark mode when the button is clicked
  darkModeToggle.addEventListener("click", function () {
    if (rootElement.classList.contains("dark")) {
      rootElement.classList.remove("dark");
      localStorage.setItem("theme", "light");
      darkModeIcon.classList.remove("fa-sun");
      darkModeIcon.classList.add("fa-moon");
    } else {
      rootElement.classList.add("dark");
      localStorage.setItem("theme", "dark");
      darkModeIcon.classList.remove("fa-moon");
      darkModeIcon.classList.add("fa-sun");
    }
  });
});

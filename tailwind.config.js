/** @type {import('tailwindcss').Config} */
/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/main/resources/templates/**/*.html", // Adjust the path to match your HTML templates
    "./src/main/resources/static/**/*.js", // Include any custom JavaScript files
  ],
  theme: {
    extend: {},
  },
  screens: {
    sm: "640px",
    md: "768px",
    lg: "1024px", // Ensure this is set as per your design
    xl: "1280px",
    "2xl": "1536px",
  },
  plugins: [require("@tailwindcss/forms")],
};

module.exports = {
  mode: "jit",
  content: [
    "./src/**/**/*.{js,ts,jsx,tsx,html,mdx}",
    "./src/**/*.{js,ts,jsx,tsx,html,mdx}",
  ],
  darkMode: "class",
  theme: {
    screens: { md: { max: "1050px" }, sm: { max: "550px" } },
    extend: {
      colors: {
        gray: {
          200: "#eeeeee",
          300: "#dddddd",
          500: "#999999",
          600: "#757575",
          800: "#404040",
        },
        blue_gray: { 100: "#d3d3d3", 800: "#343b4d", 900: "#333333" },
        black: { 900: "#000000" },
        purple: { 900: "#5f0080" },
        white: { A700: "#ffffff" },
      },
      fontFamily: { notosanskr: "Noto Sans KR", roboto: "Roboto" },
    },
  },
  plugins: [require("@tailwindcss/forms")],
};

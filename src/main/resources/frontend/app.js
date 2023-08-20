import { upload } from "./upload";

upload("#file", {
  multi: true,
  accept: [".png", ".jpg", ".jpeg", ".gif"],
  async onUpload(files) {
    console.log("Files", files);
    const formData = new FormData();

    for (let i = 0; i < files.length; i++) {
      formData.append("file", files[i]);
    }

    try {
      const response = await fetch("http://localhost:8080/cats/count ", {
        method: "POST",
        body: formData,
      });

      const data = await response.json();
      console.log(data); // Вывести ответ от сервера
    } catch (error) {
      console.error("Ошибка загрузки файла:", error);
    }
  },
});

package org.xjons.empleosdos.utils;

import java.io.File;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public class UtilesArchivos {

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public UtilesArchivos() {
        // Constructor vacío
    }

    public static String guardarArchivo(MultipartFile multiPart, String ruta) {
        String nombreOriginal = multiPart.getOriginalFilename();
        // Extraemos la extensión del archivo original
        String extension = nombreOriginal.substring(nombreOriginal.lastIndexOf("."));
        // Generamos un nombre aleatorio de 12 caracteres y agregamos la extensión
        String nombreFinal = randomAlphaNumeric(12) + extension;
        // Construimos la ruta completa donde se guardará el archivo
        String rutaCompleta = ruta + nombreFinal;

        try {
            File imageFile = new File(rutaCompleta);
            System.out.println("Archivo: " + imageFile.getAbsolutePath());
            multiPart.transferTo(imageFile);
            // Retornamos el nombre final del archivo para guardarlo en la base de datos
            return nombreFinal;
        } catch (IOException e) {
            System.out.println("Error " + e.getMessage());
            return null;
        }
    }

    private static String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }
}

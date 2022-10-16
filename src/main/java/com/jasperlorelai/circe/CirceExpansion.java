package com.jasperlorelai.circe;

import java.io.*;

import org.bukkit.OfflinePlayer;

import org.jetbrains.annotations.NotNull;

import com.jasperlorelai.circe.exeption.*;
import com.jasperlorelai.circe.parser.Parser;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class CirceExpansion extends PlaceholderExpansion {

    private static final String IDENTIFIER = "circe";
    private static final String AUTHOR = "JasperLorelai";
    private static final String VERSION = "0.0.1";
    private static final String NAME = "Circe";

    @NotNull
    public String getIdentifier() {
        return IDENTIFIER;
    }

    @NotNull
    public String getAuthor() {
        return AUTHOR;
    }

    @NotNull
    public String getVersion() {
        return VERSION;
    }

    @NotNull
    public String getName() {
        return NAME;
    }

    public String onRequest(OfflinePlayer player, @NotNull String identifier) {
        String code = identifier;
        if (code.startsWith("file:")) {
            String fileName = code.replaceFirst("file:", "")
                    .replaceFirst("\\.circe$", "")
                    .trim();

            // Read the file.
            File folder = new File(getPlaceholderAPI().getDataFolder(), "circe");
            try {
                //noinspection ResultOfMethodCallIgnored
                folder.mkdir();
                File file = new File(folder, fileName + ".circe");
                BufferedReader reader = new BufferedReader(new FileReader(file));
                StringBuilder string = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    string.append(line).append(System.lineSeparator());
                }
                code = string.toString();
                reader.close();
            } catch (SecurityException | IOException e) {
                e.printStackTrace();
            }
        }

        code = bracketPlaceholders(player, code);
        boolean hasDebug = code.startsWith("debug;");
        if (hasDebug) code = code.replaceFirst("debug;", "");

        try {
            code = Parser.create().parse(code);
        } catch (UnknownTokenException | ParserException | FunctionCallException exception) {
            return hasDebug ? exception.getMessage() : "";
        }
        return bracketPlaceholders(player, code);
    }

    private static String bracketPlaceholders(OfflinePlayer player, String string) {
        return PlaceholderAPI.setBracketPlaceholders(player, string);
    }

}

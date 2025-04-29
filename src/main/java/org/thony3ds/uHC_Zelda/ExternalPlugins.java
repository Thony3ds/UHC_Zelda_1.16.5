package org.thony3ds.uHC_Zelda;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldedit.math.BlockVector3;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldBorder;
import org.bukkit.WorldCreator;
import org.terraform.coregen.bukkit.TerraformGenerator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public final class ExternalPlugins {

    public static void pasteSchematic(Location location, String schematicName) {
        File schematic = new File("plugins/WorldEdit/schematics/" + schematicName + ".schem");
        if (!schematic.exists()) {
            Bukkit.getLogger().warning("Schematic non trouvé: " + schematicName);
            return;
        }

        try (ClipboardReader reader = ClipboardFormats.findByFile(schematic).getReader(new FileInputStream(schematic))) {
            Clipboard clipboard = reader.read();
            World adaptedWorld = BukkitAdapter.adapt(location.getWorld());

            try (EditSession editSession = WorldEdit.getInstance().newEditSession(adaptedWorld)) {
                ClipboardHolder holder = new ClipboardHolder(clipboard);
                Operation operation = holder.createPaste(editSession)
                        .to(BlockVector3.at(location.getBlockX(), location.getBlockY(), location.getBlockZ()))
                        .ignoreAirBlocks(false)
                        .build();
                Operations.complete(operation);
            } catch (WorldEditException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
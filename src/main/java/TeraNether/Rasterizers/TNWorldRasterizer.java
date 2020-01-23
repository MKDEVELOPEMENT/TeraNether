/*
 * Copyright 2019 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package TeraNether.Rasterizers;

import TeraNether.Facets.LavaLevelFacet;
import TeraNether.Facets.TNCeilingHeightFacet;
import TeraNether.Facets.TNSurfaceHeightFacet;
import org.terasology.math.ChunkMath;
import org.terasology.math.TeraMath;
import org.terasology.math.geom.Vector3i;
import org.terasology.registry.CoreRegistry;
import org.terasology.world.block.Block;
import org.terasology.world.block.BlockManager;
import org.terasology.world.chunks.CoreChunk;
import org.terasology.world.generation.Region;
import org.terasology.world.generation.WorldRasterizer;

import static TeraNether.TNWorldGenerator.TERANETHER_BORDER;

public class TNWorldRasterizer implements WorldRasterizer {
    private Block stone;
    private Block dirt;
    private Block air;
    private Block lava;

    @Override
    public void initialize() {
        stone = CoreRegistry.get(BlockManager.class).getBlock("CoreBlocks:Stone");
        dirt = CoreRegistry.get(BlockManager.class).getBlock("Inferno:BloodiedStone"); //TODO create custom block
        air = CoreRegistry.get(BlockManager.class).getBlock(BlockManager.AIR_ID);
        lava = CoreRegistry.get(BlockManager.class).getBlock("CoreBlocks:Lava");
    }

    @Override
    public void generateChunk(CoreChunk chunk, Region chunkRegion) {
        TNSurfaceHeightFacet surfaceFacet = chunkRegion.getFacet(TNSurfaceHeightFacet.class);
        TNCeilingHeightFacet ceilingFacet = chunkRegion.getFacet(TNCeilingHeightFacet.class);
        LavaLevelFacet lavaLevelFacet = chunkRegion.getFacet(LavaLevelFacet.class);

        for (Vector3i position : chunkRegion.getRegion()) {
            float surfaceHeight = surfaceFacet.getWorld(position.x, position.y);
            float ceilingHeight = surfaceFacet.getWorld(position.x, position.y);

            if (position.y>surfaceHeight && position.y<surfaceHeight+TERANETHER_BORDER){
                chunk.setBlock(ChunkMath.calcBlockPos(position), stone);
            } else if (position.y<ceilingHeight && position.y>surfaceHeight){
                chunk.setBlock(ChunkMath.calcBlockPos(position), air);
            } else if (position.y<=surfaceHeight){
                chunk.setBlock(ChunkMath.calcBlockPos(position), dirt);
            }
        }
    }
}

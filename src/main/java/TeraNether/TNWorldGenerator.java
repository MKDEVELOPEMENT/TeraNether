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
package TeraNether;

import TeraNether.Providers.LavaLevelProvider;
import TeraNether.Providers.SurfaceProvider;
import TeraNether.Providers.TNCeilingProvider;
import TeraNether.Rasterizers.TNWorldRasterizer;
import org.terasology.engine.SimpleUri;
import org.terasology.math.geom.ImmutableVector2i;
import org.terasology.registry.In;
import org.terasology.world.generation.BaseFacetedWorldGenerator;
import org.terasology.world.generation.WorldBuilder;
import org.terasology.world.generator.RegisterWorldGenerator;
import org.terasology.world.generator.plugin.WorldGeneratorPluginLibrary;
import org.terasology.core.world.generator.facetProviders.SeaLevelProvider;

@RegisterWorldGenerator(id = "TeraNether", displayName = "TeraNether World")
public class TNWorldGenerator extends BaseFacetedWorldGenerator {

    public static final int TERANETHER_DEPTH = 100000;
    public static final int TERANETHER_HEIGHT = 40;
    public static final int TERANETHER_BORDER = 20000;

    @In
    private WorldGeneratorPluginLibrary worldGeneratorPluginLibrary;

    public TNWorldGenerator(SimpleUri uri) {
        super(uri);
    }

    ImmutableVector2i spawnPos = new ImmutableVector2i(0, 0);

    @Override
    protected WorldBuilder createWorld() {
        return new WorldBuilder(worldGeneratorPluginLibrary)
                .addProvider(new SurfaceProvider(TERANETHER_DEPTH))
                .addProvider(new SeaLevelProvider(0))
                .addProvider(new TNCeilingProvider(TERANETHER_HEIGHT))
                .addProvider(new LavaLevelProvider())
                .addRasterizer(new TNWorldRasterizer());
    }
}
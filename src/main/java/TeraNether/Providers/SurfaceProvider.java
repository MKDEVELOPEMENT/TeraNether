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
package TeraNether.Providers;

import TeraNether.Facets.TNSurfaceHeightFacet;
import org.terasology.math.geom.BaseVector2i;
import org.terasology.math.geom.Rect2i;
import org.terasology.math.geom.Vector2f;
import org.terasology.utilities.procedural.Noise;
import org.terasology.utilities.procedural.SimplexNoise;
import org.terasology.utilities.procedural.SubSampledNoise;
import org.terasology.world.generation.Border3D;
import org.terasology.world.generation.FacetProvider;
import org.terasology.world.generation.GeneratingRegion;
import org.terasology.world.generation.Produces;

@Produces(TNSurfaceHeightFacet.class)
public class SurfaceProvider implements FacetProvider {
    private Noise surfaceNoise;
    private int netherDepth;

    public SurfaceProvider(int depth){
        this.netherDepth = depth;
    }

    @Override
    public void setSeed(long seed) {
        surfaceNoise = new SubSampledNoise(new SimplexNoise(seed), new Vector2f(0.01f,0.01f), 1);
    }

    @Override
    public void process(GeneratingRegion region) {
        Border3D border = region.getBorderForFacet(TNSurfaceHeightFacet.class);
        TNSurfaceHeightFacet facet = new TNSurfaceHeightFacet(region.getRegion(), border);
        facet.setBaseSurfaceHeight(netherDepth);

        Rect2i processRegion = facet.getWorldRegion();
        for (BaseVector2i position : processRegion.contents()) {
            facet.setWorld(position, surfaceNoise.noise(position.x(), position.y())*10 - netherDepth);
        }

        region.setRegionFacet(TNSurfaceHeightFacet.class, facet);
    }
}
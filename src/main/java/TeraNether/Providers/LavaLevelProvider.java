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

import TeraNether.Facets.LavaLevelFacet;
import TeraNether.Facets.TNSurfaceHeightFacet;
import org.terasology.world.generation.*;

@Produces(LavaLevelFacet.class)
@Requires(@Facet(TNSurfaceHeightFacet.class))
public class LavaLevelProvider implements FacetProvider {

    private int lavaLevel;

    public LavaLevelProvider() {
        lavaLevel = 0;
    }

    public LavaLevelProvider(int lavaLevel) {
        this.lavaLevel = lavaLevel;
    }

    @Override
    public void process(GeneratingRegion region) {
        Border3D border = region.getBorderForFacet(LavaLevelFacet.class);
        LavaLevelFacet facet = new LavaLevelFacet(region.getRegion(), border);
        TNSurfaceHeightFacet surfaceHeightFacet = region.getRegionFacet(TNSurfaceHeightFacet.class);
        facet.setLavaLevel(lavaLevel - surfaceHeightFacet.getBaseSurfaceHeight());
        region.setRegionFacet(LavaLevelFacet.class, facet);
    }
}
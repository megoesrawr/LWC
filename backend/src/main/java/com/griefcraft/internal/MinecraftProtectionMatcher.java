/*
 * Copyright (c) 2011, 2012, Tyler Blair
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ''AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and contributors and should not be interpreted as representing official policies,
 * either expressed or implied, of anybody else.
 */

package com.griefcraft.internal;

import com.griefcraft.LWC;
import com.griefcraft.ProtectionMatcher;
import com.griefcraft.ProtectionSet;
import com.griefcraft.ServerLayer;
import com.griefcraft.world.Block;

public class MinecraftProtectionMatcher implements ProtectionMatcher {

    /**
     * The lwc instance
     */
    private LWC lwc;

    public MinecraftProtectionMatcher(LWC lwc) {
        this.lwc = lwc;
    }

    public ProtectionSet matchProtection(Block base) {
        ServerLayer layer = lwc.getServerLayer();
        ProtectionSet blocks = new ProtectionSet(lwc.getDatabase());

        int baseType = base.getType();

        // first add the base block, as it must exist on the protection if it matches
        blocks.add(getBlockType(base), base);

        // Double chests
        // TODO no literals ?
        if (baseType == 54) {
            Block adjacentChest = base.findBlockRelative(54);

            if (adjacentChest != null) {
                blocks.add(getBlockType(adjacentChest), adjacentChest);
            }
        }

        return blocks;
    }

    /**
     * Get the BlockType that should be used for the given block
     *
     * @param block
     * @return
     */
    private ProtectionSet.BlockType getBlockType(Block block) {
        return lwc.getServerLayer().isBlockProtectable(block) ? ProtectionSet.BlockType.PROTECTABLE : ProtectionSet.BlockType.MATCHABLE;
    }

}

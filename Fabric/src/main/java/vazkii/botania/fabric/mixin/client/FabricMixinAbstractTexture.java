/*
 * This class is distributed as part of the Botania Mod.
 * Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 */
package vazkii.botania.fabric.mixin.client;

import net.minecraft.client.renderer.texture.AbstractTexture;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import vazkii.botania.fabric.client.ExtendedTexture;

@Mixin(AbstractTexture.class)
public abstract class FabricMixinAbstractTexture implements ExtendedTexture {
	@Shadow
	protected boolean blur;

	@Shadow
	protected boolean mipmap;

	@Shadow
	public abstract void setFilter(boolean bilinear, boolean mipmap);

	@Unique
	private boolean lastBilinear;

	@Unique
	private boolean lastMipmap;

	@Override
	public void setFilterSave(boolean bilinear, boolean mipmap) {
		this.lastBilinear = this.blur;
		this.lastMipmap = this.mipmap;
		setFilter(bilinear, mipmap);
	}

	@Override
	public void restoreLastFilter() {
		setFilter(this.lastBilinear, this.lastMipmap);
	}
}

/*
 * This class is distributed as part of the Botania Mod.
 * Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 */
package vazkii.botania.common.integration.crafttweaker;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionAddRecipe;
import com.blamejared.crafttweaker_annotations.annotations.Document;

import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

import org.openzen.zencode.java.ZenCodeType;

import vazkii.botania.api.recipe.ITerraPlateRecipe;
import vazkii.botania.common.crafting.ModRecipeTypes;
import vazkii.botania.common.crafting.RecipeTerraPlate;

import java.util.Arrays;

/**
 * @docParam this <recipetype:botania:terra_plate>
 */
@Document("mods/Botania/TerraPlate")
@ZenRegister
@ZenCodeType.Name("mods.botania.TerraPlate")
public class TerraPlateRecipeManager implements IRecipeManager {

	/**
	 * Adds a terra plate recipe
	 * 
	 * @param name   Name of the recipe to add
	 * @param output Output item
	 * @param mana   Recipe mana cost
	 * @param inputs Input items
	 * @docParam name "terra_plate_test"
	 * @docParam output <item:minecraft:diamond>
	 * @docParam mana 20000
	 * @docParam inputs <item:minecraft:dirt>, <item:minecraft:cobblestone>, <item:minecraft:gravel>
	 */
	@ZenCodeType.Method
	public void addRecipe(String name, IItemStack output, int mana, IIngredient... inputs) {
		name = fixRecipeName(name);
		ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);
		NonNullList<Ingredient> inputList =
				NonNullList.from(Ingredient.EMPTY, Arrays.stream(inputs).map(IIngredient::asVanillaIngredient).toArray(
						Ingredient[]::new));
		CraftTweakerAPI.apply(new ActionAddRecipe(this,
				new RecipeTerraPlate(resourceLocation,
						mana,
						inputList,
						output.getInternal()),
				""));
	}

	@Override
	public IRecipeType<ITerraPlateRecipe> getRecipeType() {
		return ModRecipeTypes.TERRA_PLATE_TYPE;
	}
}

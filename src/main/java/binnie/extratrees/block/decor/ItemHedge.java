package binnie.extratrees.block.decor;

import binnie.extratrees.genetics.ExtraTreeSpecies;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemHedge extends ItemBlock {
    public ItemHedge(final Block block) {
        super(block);
    }

    @Override
    public String getItemStackDisplayName(final ItemStack item) {
        final int meta = item.getItemDamage();
        return ExtraTreeSpecies.LeafType.values()[meta % 6].descript + ((meta >= 8) ? " Full" : "") + " Hedge";
    }
}

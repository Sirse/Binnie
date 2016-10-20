package binnie.genetics.item;

import binnie.genetics.CreativeTabGenetics;
import binnie.genetics.Genetics;
import binnie.genetics.core.GeneticsGUI;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemAnalyst extends Item {
//	@Override
//	@SideOnly(Side.CLIENT)
//	public void registerIcons(final IIconRegister register) {
//		this.itemIcon = Genetics.proxy.getIcon(register, "analyst");
//	}

    public ItemAnalyst() {
        this.setCreativeTab(CreativeTabGenetics.instance);
        this.setUnlocalizedName("analyst");
        this.setMaxStackSize(1);
        setRegistryName("analyst");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World world, EntityPlayer player, EnumHand hand) {
        Genetics.proxy.openGui(GeneticsGUI.Analyst, player, new BlockPos((int) player.posX, (int) player.posY, (int) player.posZ));
        return super.onItemRightClick(itemStackIn, world, player, hand);
    }

    @Override
    public String getItemStackDisplayName(final ItemStack i) {
        return "Analyst";
    }
}

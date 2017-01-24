package binnie.extratrees.machines.distillery;

import binnie.core.machines.Machine;
import binnie.core.machines.network.INetwork;
import binnie.core.machines.power.ComponentProcessSetCost;
import binnie.core.machines.power.ErrorState;
import binnie.core.machines.power.IProcess;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;

import java.util.Map;

public class DistilleryLogic extends ComponentProcessSetCost implements IProcess, INetwork.SendGuiNBT, INetwork.RecieveGuiNBT {
	public static final int INPUT_FLUID_AMOUNT = 1000;

	public FluidStack currentFluid;
	public int level;

	public DistilleryLogic(final Machine machine) {
		super(machine, 16000, 800);
		this.currentFluid = null;
		this.level = 0;
	}

	@Override
	public float getEnergyPerTick() {
		return 2.0f;
	}

	@Override
	public int getProcessLength() {
		return 2000 + 800 * this.level;
	}

	@Override
	public void readFromNBT(final NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.level = nbt.getByte("dlevel");
	}

	@Override
	public NBTTagCompound writeToNBT(final NBTTagCompound nbt2) {
		NBTTagCompound nbt = super.writeToNBT(nbt2);
		nbt.setByte("dlevel", (byte) this.level);
		return nbt;
	}

	@Override
	public ErrorState canWork() {
		if (this.getUtil().isTankEmpty(DistilleryMachine.TANK_INPUT) && this.currentFluid == null) {
			return new ErrorState.InsufficientLiquid("No Input Liquid", DistilleryMachine.TANK_INPUT);
		}
		return super.canWork();
	}

	@Override
	public ErrorState canProgress() {
		if (this.currentFluid == null) {
			return new ErrorState("Distillery Empty", "No liquid in Distillery");
		}

		FluidStack fluidInOutputTank = this.getUtil().getFluid(DistilleryMachine.TANK_OUTPUT);
		if (fluidInOutputTank != null) {
			FluidStack inputFluid = this.getUtil().getFluid(DistilleryMachine.TANK_INPUT);
			FluidStack recipeOutput = DistilleryRecipes.getOutput(inputFluid, this.level);
			if (recipeOutput != null && !recipeOutput.isFluidEqual(fluidInOutputTank)) {
				return new ErrorState.TankSpace("Different fluid in tank", DistilleryMachine.TANK_OUTPUT);
			}
		}

		return super.canProgress();
	}

	@Override
	protected void onFinishTask() {
		final FluidStack output = DistilleryRecipes.getOutput(this.currentFluid, this.level);
		this.getUtil().fillTank(DistilleryMachine.TANK_OUTPUT, output);
	}

	@Override
	protected void onTickTask() {
	}

	@Override
	public void recieveGuiNBT(final Side side, final EntityPlayer player, final String name, final NBTTagCompound nbt) {
		if (name.equals("still-level")) {
			this.level = nbt.getByte("i");
			this.setProgress(0);
		}
		if (name.equals("still-recipe")) {
			if (nbt.getBoolean("null")) {
				this.currentFluid = null;
			} else {
				this.currentFluid = FluidStack.loadFluidStackFromNBT(nbt);
			}
		}
	}

	@Override
	public void sendGuiNBT(final Map<String, NBTTagCompound> data) {
		final NBTTagCompound nbt = new NBTTagCompound();
		if (this.currentFluid == null) {
			nbt.setBoolean("null", true);
		} else {
			this.currentFluid.writeToNBT(nbt);
		}
		data.put("still-recipe", nbt);
		nbt.setByte("i", (byte) this.level);
		data.put("still-level", nbt);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (this.canWork() == null && this.currentFluid == null && this.getUtil().getTank(DistilleryMachine.TANK_INPUT).getFluidAmount() >= INPUT_FLUID_AMOUNT) {
			this.currentFluid = this.getUtil().drainTank(DistilleryMachine.TANK_INPUT, INPUT_FLUID_AMOUNT);
		}
	}

	@Override
	public String getTooltip() {
		if (this.currentFluid == null) {
			return "Empty";
		}
		FluidStack output = DistilleryRecipes.getOutput(this.currentFluid, this.level);
		if (output == null) {
			return "Empty";
		}
		return "Creating " + output.getLocalizedName();
	}
}
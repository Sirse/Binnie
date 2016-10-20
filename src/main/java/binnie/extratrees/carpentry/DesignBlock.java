package binnie.extratrees.carpentry;

import binnie.extratrees.api.IDesign;
import binnie.extratrees.api.IDesignMaterial;
import binnie.extratrees.api.IDesignSystem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class DesignBlock {
    IDesign design;
    IDesignMaterial primaryMaterial;
    IDesignMaterial secondaryMaterial;
    int rotation;
    EnumFacing facing;
    boolean panel;

    @Override
    public String toString() {
        return super.toString() + " { design:" + this.design + " }, { primary:" + this.primaryMaterial + " }, { secondary:" + this.secondaryMaterial + " }, { rotation:" + this.rotation + " }, { facing:" + this.facing + " }";
    }

    public IDesign getDesign() {
        return this.design;
    }

    public IDesignMaterial getPrimaryMaterial() {
        return this.primaryMaterial;
    }

    public IDesignMaterial getSecondaryMaterial() {
        return this.secondaryMaterial;
    }

    DesignBlock(final IDesignSystem system, final IDesignMaterial primaryWood, final IDesignMaterial secondaryWood, final IDesign design, final int rotation, final EnumFacing dir) {
        this.rotation = 0;
        this.facing = EnumFacing.UP;
        this.panel = false;
        this.design = design;
        this.primaryMaterial = primaryWood;
        this.secondaryMaterial = secondaryWood;
        this.rotation = rotation;
        this.facing = dir;
        if (design == null) {
            this.design = EnumDesign.Blank;
        }
        if (primaryWood == null) {
            this.primaryMaterial = system.getDefaultMaterial();
        }
        if (secondaryWood == null) {
            this.secondaryMaterial = system.getDefaultMaterial();
        }
        if (this.rotation > 3 || this.rotation < 0) {
            this.rotation = 0;
        }
        if (this.facing == null) {
            this.facing = EnumFacing.UP;
        }
    }

    public int getPrimaryColour() {
        return this.getPrimaryMaterial().getColour();
    }

    public int getSecondaryColour() {
        return this.getSecondaryMaterial().getColour();
    }

    EnumFacing getRotation(final EnumFacing dir, final ModuleCarpentry.Axis axis) {
        if (axis == ModuleCarpentry.Axis.Y) {
            switch (dir) {
                case EAST: {
                    return EnumFacing.NORTH;
                }
                case NORTH: {
                    return EnumFacing.WEST;
                }
                case SOUTH: {
                    return EnumFacing.EAST;
                }
                case WEST: {
                    return EnumFacing.SOUTH;
                }
                default: {
                    return dir;
                }
            }
        } else if (axis == ModuleCarpentry.Axis.X) {
            switch (dir) {
                case EAST: {
                    return EnumFacing.UP;
                }
                case UP: {
                    return EnumFacing.WEST;
                }
                case WEST: {
                    return EnumFacing.DOWN;
                }
                case DOWN: {
                    return EnumFacing.EAST;
                }
                default: {
                    return dir;
                }
            }
        } else {
            switch (dir) {
                case SOUTH: {
                    return EnumFacing.UP;
                }
                case UP: {
                    return EnumFacing.NORTH;
                }
                case NORTH: {
                    return EnumFacing.DOWN;
                }
                case DOWN: {
                    return EnumFacing.SOUTH;
                }
                default: {
                    return dir;
                }
            }
        }
    }

//	public ILayout getLayout(EnumFacing dir) {
//		EnumFacing adjustedDir;
//		dir = (adjustedDir = dir.getRotation(EnumFacing.DOWN));
//		switch (this.getFacing()) {
//		case DOWN: {
//			adjustedDir = adjustedDir.getRotation(EnumFacing.EAST);
//			adjustedDir = adjustedDir.getRotation(EnumFacing.EAST);
//			break;
//		}
//		case EAST: {
//			adjustedDir = adjustedDir.getRotation(EnumFacing.EAST);
//			adjustedDir = adjustedDir.getRotation(EnumFacing.NORTH);
//			break;
//		}
//		case NORTH: {
//			adjustedDir = adjustedDir.getRotation(EnumFacing.EAST);
//			adjustedDir = adjustedDir.getRotation(EnumFacing.SOUTH);
//			adjustedDir = adjustedDir.getRotation(EnumFacing.SOUTH);
//			break;
//		}
//		case SOUTH: {
//			adjustedDir = adjustedDir.getRotation(EnumFacing.EAST);
//			break;
//		}
//		case WEST: {
//			adjustedDir = adjustedDir.getRotation(EnumFacing.EAST);
//			adjustedDir = adjustedDir.getRotation(EnumFacing.SOUTH);
//			break;
//		}
//		}
//		for (int i = 0; i < this.rotation; ++i) {
//			adjustedDir = adjustedDir.getRotation(EnumFacing.DOWN);
//		}
//		ILayout layout = null;
//		switch (adjustedDir) {
//		case EAST: {
//			layout = this.getDesign().getEastPattern();
//			break;
//		}
//		case NORTH: {
//			layout = this.getDesign().getNorthPattern();
//			break;
//		}
//		case SOUTH: {
//			layout = this.getDesign().getSouthPattern();
//			break;
//		}
//		case WEST: {
//			layout = this.getDesign().getWestPattern();
//			break;
//		}
//		case DOWN: {
//			layout = this.getDesign().getBottomPattern();
//			break;
//		}
//		default: {
//			layout = this.getDesign().getTopPattern();
//			break;
//		}
//		}
//		Label_1107: {
//			switch (this.getFacing()) {
//			case UP: {
//				if (dir == EnumFacing.DOWN || dir == EnumFacing.UP) {
//					for (int j = 0; j < this.rotation; ++j) {
//						layout = layout.rotateRight();
//					}
//					break;
//				}
//				break;
//			}
//			case DOWN: {
//				switch (dir) {
//				case UP:
//				case DOWN: {
//					layout = layout.flipVertical();
//					break;
//				}
//				case EAST:
//				case NORTH:
//				case SOUTH:
//				case WEST: {
//					layout = layout.rotateRight().rotateRight();
//					break;
//				}
//				}
//				if (dir == EnumFacing.DOWN || dir == EnumFacing.UP) {
//					for (int j = 0; j < this.rotation; ++j) {
//						layout = layout.rotateLeft();
//					}
//					break;
//				}
//				break;
//			}
//			case EAST: {
//				switch (dir) {
//				case SOUTH:
//				case UP: {
//					layout = layout.rotateRight();
//					break;
//				}
//				case NORTH: {
//					layout = layout.rotateLeft();
//					break;
//				}
//				case DOWN: {
//					layout = layout.rotateLeft().flipHorizontal();
//					break;
//				}
//				case WEST: {
//					layout = layout.flipHorizontal();
//					break;
//				}
//				}
//				if (dir == EnumFacing.EAST) {
//					for (int j = 0; j < this.rotation; ++j) {
//						layout = layout.rotateRight();
//					}
//				}
//				if (dir == EnumFacing.WEST) {
//					for (int j = 0; j < this.rotation; ++j) {
//						layout = layout.rotateLeft();
//					}
//					break;
//				}
//				break;
//			}
//			case WEST: {
//				switch (dir) {
//				case NORTH: {
//					layout = layout.rotateRight();
//					break;
//				}
//				case SOUTH:
//				case UP: {
//					layout = layout.rotateLeft();
//					break;
//				}
//				case DOWN: {
//					layout = layout.rotateLeft().flipVertical();
//					break;
//				}
//				case EAST: {
//					layout = layout.flipHorizontal();
//					for (int j = 0; j < this.rotation; ++j) {
//						layout = layout.rotateLeft();
//					}
//					break;
//				}
//				case WEST: {
//					for (int j = 0; j < this.rotation; ++j) {
//						layout = layout.rotateRight();
//					}
//					break;
//				}
//				}
//				break;
//			}
//			case NORTH: {
//				switch (dir) {
//				case WEST: {
//					layout = layout.rotateLeft();
//					break Label_1107;
//				}
//				case EAST: {
//					layout = layout.rotateRight();
//					break Label_1107;
//				}
//				case DOWN: {
//					layout = layout.flipHorizontal();
//					break Label_1107;
//				}
//				case SOUTH: {
//					layout = layout.flipHorizontal();
//					for (int j = 0; j < this.rotation; ++j) {
//						layout = layout.rotateLeft();
//					}
//					break Label_1107;
//				}
//				case NORTH: {
//					for (int j = 0; j < this.rotation; ++j) {
//						layout = layout.rotateRight();
//					}
//					break Label_1107;
//				}
//				default: {
//					break Label_1107;
//				}
//				}
//
//			}
//			case SOUTH: {
//				switch (dir) {
//				case EAST: {
//					layout = layout.rotateLeft();
//					break Label_1107;
//				}
//				case WEST: {
//					layout = layout.rotateRight();
//					break Label_1107;
//				}
//				case UP: {
//					layout = layout.rotateRight().rotateRight();
//					break Label_1107;
//				}
//				case DOWN: {
//					layout = layout.flipVertical();
//					break Label_1107;
//				}
//				case NORTH: {
//					layout = layout.flipHorizontal();
//					for (int j = 0; j < this.rotation; ++j) {
//						layout = layout.rotateLeft();
//					}
//					break Label_1107;
//				}
//				case SOUTH: {
//					for (int j = 0; j < this.rotation; ++j) {
//						layout = layout.rotateRight();
//					}
//					break Label_1107;
//				}
//				default: {
//					break Label_1107;
//				}
//				}
//
//			}
//			}
//		}
//		return layout;
//	}
//
//	public IIcon getPrimaryIcon(final IDesignSystem system, final EnumFacing dir) {
//		final ILayout l = this.getLayout(dir);
//		return (l == null) ? null : l.getPrimaryIcon(system);
//	}
//
//	public IIcon getSecondaryIcon(final IDesignSystem system, final EnumFacing dir) {
//		final ILayout l = this.getLayout(dir);
//		return (l == null) ? null : l.getSecondaryIcon(system);
//	}
//
//	public IIcon getIcon(final IDesignSystem system, final boolean secondary, final EnumFacing dir) {
//		return secondary ? this.getSecondaryIcon(system, dir) : this.getPrimaryIcon(system, dir);
//	}

    public EnumFacing getFacing() {
        return this.facing;
    }

    public int getRotation() {
        return this.rotation;
    }

    public void rotate(final int face, final ItemStack hammer, final EntityPlayer player, final World world, final int x, final int y, final int z) {
//		final EnumFacing dir = EnumFacing.getOrientation(face);
//		final IToolHammer hammerI = (IToolHammer) hammer.getItem();
//		if (player.isSneaking()) {
//			if (this.panel) {
//				EnumFacing newFacing = this.getFacing();
//				do {
//					newFacing = EnumFacing.getOrientation(newFacing.ordinal() + 1);
//					if (newFacing == EnumFacing.UNKNOWN) {
//						newFacing = EnumFacing.DOWN;
//					}
//				} while (newFacing != this.getFacing() && !BlockCarpentryPanel.isValidPanelPlacement(world, x, y, z, newFacing));
//				if (newFacing != this.getFacing()) {
//					hammerI.onHammerUsed(hammer, player);
//				}
//				this.setFacing(newFacing);
//			}
//			else {
//				if (dir != this.getFacing()) {
//					hammerI.onHammerUsed(hammer, player);
//				}
//				this.setFacing(dir);
//			}
//		}
//		else {
//			++this.rotation;
//			hammerI.onHammerUsed(hammer, player);
//		}
//		if (this.rotation > 3) {
//			this.rotation = 0;
//		}
//		if (this.rotation < 0) {
//			this.rotation = 3;
//		}
    }

    public void setFacing(final EnumFacing facing) {
        this.facing = facing;
    }

    public int getBlockMetadata(final IDesignSystem system) {
        return ModuleCarpentry.getBlockMetadata(system, this);
    }

    public int getItemMetadata(final IDesignSystem system) {
        return ModuleCarpentry.getItemMetadata(system, this);
    }

    public void setPanel() {
        this.panel = true;
    }

    public String getString() {
        String type = "";
        if (this.getPrimaryMaterial() != this.getSecondaryMaterial()) {
            type = this.getPrimaryMaterial().getName() + " and " + this.getSecondaryMaterial().getName();
        } else {
            type = this.getPrimaryMaterial().getName();
        }
        return super.toString() + " " + "{" + type + " " + this.getDesign().getName() + " " + (this.panel ? "Panel" : "Tile") + ", Facing:" + this.getFacing() + ", Rotation:" + this.getRotation() + "}";
    }
}

package fr.skayizen.azuria.Utils.Classes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ItemBuilder {

    private Material material;
    private int amount = 1;
    private short durability = 0;
    private String displayName;
    private List<String> lore = Lists.newArrayList();
    private boolean unbreakable = false;
    private final Set<ItemFlag> itemFlags = Sets.newHashSet();
    private final Map<Enchantment, Integer> enchantments = Maps.newHashMap();
    private final Map<Enchantment, Integer> storedEnchantments = Maps.newHashMap();
    private final List<Applier> nbtAppliers = Lists.newArrayList();

    public ItemBuilder(Material material) {
        withMaterial(material);
    }

    public ItemBuilder(ItemStack stack) {
        this(stack.getType());
        this.amount = stack.getAmount();
        this.durability = stack.getDurability();
        if (stack.hasItemMeta()) {
            ItemMeta meta = stack.getItemMeta();
            this.displayName = meta.getDisplayName();
            this.lore = meta.hasLore() ? meta.getLore() : Lists.newArrayList();
            this.unbreakable = meta.spigot().isUnbreakable();
            this.itemFlags.addAll(meta.getItemFlags());
            if(meta instanceof EnchantmentStorageMeta)
                this.storedEnchantments.putAll(((EnchantmentStorageMeta) meta).getStoredEnchants());
        }
        this.enchantments.putAll(stack.getEnchantments());

    }

    public ItemBuilder(ItemBuilder builder) {
        this(builder.getMaterial());
        this.amount = builder.amount;
        this.durability = builder.durability;
        this.displayName = builder.displayName;
        this.lore = Lists.newArrayList(builder.lore);
        this.unbreakable = builder.unbreakable;
        this.itemFlags.addAll(Lists.newArrayList(builder.itemFlags));
        this.enchantments.putAll(Maps.newHashMap(builder.enchantments));
        this.nbtAppliers.addAll(Lists.newArrayList(builder.nbtAppliers));
    }

    public ItemStack build() {
        ItemStack stack = new ItemStack(this.material, this.amount, this.durability);
        ItemMeta meta = stack.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(this.displayName);
            meta.setLore(this.lore);
            meta.spigot().setUnbreakable(this.unbreakable);
            meta.addItemFlags(this.itemFlags.<ItemFlag>toArray(new ItemFlag[0]));
            if(meta instanceof EnchantmentStorageMeta)
                this.storedEnchantments.forEach((key, value) -> ((EnchantmentStorageMeta) meta).addStoredEnchant(key, value, true));
            stack.setItemMeta(meta);
        }
        stack.addUnsafeEnchantments(this.enchantments);
        for (Applier applier : this.nbtAppliers)
            stack = applier.apply(stack);
        return stack;
    }

    public ItemBuilder deepClone() {
        return new ItemBuilder(this);
    }

    public ItemBuilder withMaterial(Material material) {
        this.material = material;
        return this;
    }

    public ItemBuilder withAmount(int amount) {
        this.amount = amount;
        return this;
    }


    public ItemBuilder withDurability(short durability) {
        this.durability = durability;
        return this;
    }

    public ItemBuilder withDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public ItemBuilder withDisplayNameColoured(String displayName) {
        this.displayName = Messages.color(displayName);
        return this;
    }

    public ItemBuilder withLore(Iterable<? extends String> lore) {
        this.lore = Lists.newArrayList(lore);
        return this;
    }

    public ItemBuilder withLoreColoured(Iterable<? extends String> lore) {
        this.lore = Messages.color(lore);
        return this;
    }

    public ItemBuilder withUnbreakable(boolean unbreakable) {
        this.unbreakable = unbreakable;
        return this;
    }

    public ItemBuilder withItemFlag(ItemFlag... flag) {
        this.itemFlags.addAll(Arrays.asList(flag));
        return this;
    }

    public ItemBuilder withEnchantment(Enchantment enchantment, int level) {
        this.enchantments.put(enchantment, level);
        return this;
    }

    public ItemBuilder withStoredEnchantment(Enchantment enchantment, int level) {
        this.storedEnchantments.put(enchantment, level);
        return this;
    }

    public ItemBuilder withEnchantments(Map<Enchantment, Integer> enchantments) {
        this.enchantments.putAll(enchantments);
        return this;
    }


    public Material getMaterial() {
        return this.material;
    }

    public int getAmount() {
        return this.amount;
    }

    public short getDurability() {
        return this.durability;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public List<String> getLore() {
        return this.lore;
    }

    public boolean isUnbreakable() {
        return this.unbreakable;
    }

    public Set<ItemFlag> getItemFlags() {
        return this.itemFlags;
    }

    public Map<Enchantment, Integer> getEnchantments() {
        return this.enchantments;
    }

    protected List<Applier> getNBTAppliers() {
        return this.nbtAppliers;
    }

    interface Applier {
        ItemStack apply(ItemStack param1ItemStack);
    }
}

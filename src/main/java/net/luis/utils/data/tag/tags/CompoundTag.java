package net.luis.utils.data.tag.tags;

import com.google.common.collect.Maps;
import net.luis.utils.data.tag.Tag;
import net.luis.utils.data.tag.TagType;
import net.luis.utils.data.tag.TagTypes;
import net.luis.utils.data.tag.exception.LoadTagException;
import net.luis.utils.data.tag.exception.SaveTagException;
import net.luis.utils.data.tag.tags.collection.ListTag;
import net.luis.utils.data.tag.tags.collection.array.ByteArrayTag;
import net.luis.utils.data.tag.tags.collection.array.IntArrayTag;
import net.luis.utils.data.tag.tags.collection.array.LongArrayTag;
import net.luis.utils.data.tag.tags.numeric.*;
import net.luis.utils.data.tag.visitor.TagVisitor;
import net.luis.utils.util.Equals;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Luis-st
 *
 */

public class CompoundTag implements Tag {
	
	public static final TagType<CompoundTag> TYPE = new TagType<>() {
		@Override
		public @NotNull CompoundTag load(DataInput input) throws LoadTagException {
			try {
				int size = input.readInt();
				Map<String, Tag> data = Maps.newHashMap();
				for (int i = 0; i < size; i++) {
					byte type = input.readByte();
					TagType<?> tagType = TagTypes.getType(type);
					if (type != END_TAG) {
						int length = input.readInt();
						char[] array = new char[length];
						for (int j = 0; j < length; j++) {
							array[j] = (char) (input.readInt() - length * 2);
						}
						Tag tag = tagType.load(input);
						data.put(new String(array), tag);
					}
				}
				return new CompoundTag(data);
			} catch (IOException e) {
				throw new LoadTagException(e);
			}
		}
		
		@Override
		public @NotNull String getName() {
			return "compound_tag";
		}
		
		@Override
		public @NotNull String getVisitorName() {
			return "CompoundTag";
		}
	};
	
	private final Map<String, Tag> data;
	
	public CompoundTag() {
		this(Maps.newHashMap());
	}
	
	private CompoundTag(Map<String, Tag> data) {
		this.data = Maps.newHashMap(data);
	}
	
	@Override
	public void save(DataOutput output) throws SaveTagException {
		try {
			output.writeInt(this.data.size());
			for (String key : this.data.keySet()) {
				Tag tag = this.data.get(key);
				output.writeByte(tag.getId());
				if (tag.getId() != 0) {
					int[] array = key.chars().toArray();
					output.writeInt(array.length);
					for (int i : array) {
						output.writeInt(i + array.length * 2);
					}
					tag.save(output);
				}
			}
		} catch (IOException e) {
			throw new SaveTagException(e);
		}
	}
	
	@Override
	public byte getId() {
		return COMPOUND_TAG;
	}
	
	@Override
	public @NotNull TagType<CompoundTag> getType() {
		return TYPE;
	}
	
	@Override
	public @NotNull CompoundTag copy() {
		return new CompoundTag(Map.copyOf(this.data));
	}
	
	@Override
	public void accept(TagVisitor visitor) {
		visitor.visitCompound(this);
	}
	
	public int size() {
		return this.data.size();
	}
	
	public Set<String> getAllKeys() {
		return this.data.keySet();
	}
	
	public boolean contains(String key) {
		return this.data.containsKey(key);
	}
	
	public boolean contains(String key, int type) {
		int tagType = this.getTagType(key);
		if (tagType == type) {
			return true;
		} else if (type != 99) {
			return false;
		} else {
			return tagType == BYTE_TAG || tagType == SHORT_TAG || tagType == INT_TAG || tagType == LONG_TAG || tagType == FLOAT_TAG || tagType == DOUBLE_TAG;
		}
	}
	
	public Tag put(String key, Tag tag) {
		if (tag == null) {
			throw new IllegalArgumentException("Invalid tag with value null for key: " + key);
		}
		return this.data.put(key, tag);
	}
	
	public void putByte(String key, byte data) {
		this.put(key, ByteTag.valueOf(data));
	}
	
	public void putShort(String key, short data) {
		this.put(key, ShortTag.valueOf(data));
	}
	
	public void putInt(String key, int data) {
		this.put(key, IntTag.valueOf(data));
	}
	
	public void putLong(String key, long data) {
		this.put(key, LongTag.valueOf(data));
	}
	
	public void putFloat(String key, float data) {
		this.put(key, FloatTag.valueOf(data));
	}
	
	public void putDouble(String key, double data) {
		this.put(key, DoubleTag.valueOf(data));
	}
	
	public void putString(String key, String data) {
		this.put(key, StringTag.valueOf(data));
	}
	
	public void putByteArray(String key, byte[] data) {
		this.put(key, new ByteArrayTag(data));
	}
	
	public void putByteArray(String key, List<Byte> data) {
		this.put(key, new ByteArrayTag(data));
	}
	
	public void putIntArray(String key, int[] data) {
		this.put(key, new IntArrayTag(data));
	}
	
	public void putIntArray(String key, List<Integer> data) {
		this.put(key, new IntArrayTag(data));
	}
	
	public void putLongArray(String key, long[] data) {
		this.put(key, new LongArrayTag(data));
	}
	
	public void putLongArray(String key, List<Long> data) {
		this.put(key, new LongArrayTag(data));
	}
	
	public void putList(String key, ListTag data) {
		this.put(key, data);
	}
	
	public void putCompound(String key, CompoundTag data) {
		this.put(key, data);
	}
	
	public void putBoolean(String key, boolean data) {
		this.put(key, ByteTag.valueOf(data));
	}
	
	public byte getTagType(String key) {
		Tag tag = this.get(key);
		return tag == null ? 0 : tag.getId();
	}
	
	public Tag get(String key) {
		return this.data.get(key);
	}
	
	public byte getByte(String key) {
		try {
			if (this.contains(key, PRIMITIVE_TAG)) {
				return ((NumericTag) this.get(key)).getAsByte();
			}
		} catch (ClassCastException ignored) {
			
		}
		return (byte) 0;
	}
	
	public short getShort(String key) {
		try {
			if (this.contains(key, PRIMITIVE_TAG)) {
				return ((NumericTag) this.get(key)).getAsShort();
			}
		} catch (ClassCastException ignored) {
			
		}
		return (short) 0;
	}
	
	public int getInt(String key) {
		try {
			if (this.contains(key, PRIMITIVE_TAG)) {
				return ((NumericTag) this.get(key)).getAsInt();
			}
		} catch (ClassCastException ignored) {
			
		}
		return 0;
	}
	
	public long getLong(String key) {
		try {
			if (this.contains(key, PRIMITIVE_TAG)) {
				return ((NumericTag) this.get(key)).getAsLong();
			}
		} catch (ClassCastException ignored) {
			
		}
		return 0;
	}
	
	public float getFloat(String key) {
		try {
			if (this.contains(key, PRIMITIVE_TAG)) {
				return ((NumericTag) this.get(key)).getAsFloat();
			}
		} catch (ClassCastException ignored) {
			
		}
		return (float) 0;
	}
	
	public double getDouble(String key) {
		try {
			if (this.contains(key, PRIMITIVE_TAG)) {
				return ((NumericTag) this.get(key)).getAsDouble();
			}
		} catch (ClassCastException ignored) {
			
		}
		return 0;
	}
	
	public String getString(String key) {
		try {
			if (this.contains(key, STRING_TAG)) {
				return this.get(key).getAsString();
			}
		} catch (ClassCastException e) {
			throw new RuntimeException("Fail to read tag of type: " + TagTypes.getType(this.getTagType(key)).getName() + ", since it's corrupt");
		}
		return "";
	}
	
	public byte[] getByteArray(String key) {
		try {
			if (this.contains(key, BYTE_ARRAY_TAG)) {
				return ((ByteArrayTag) this.get(key)).getAsByteArray();
			}
		} catch (ClassCastException e) {
			throw new RuntimeException("Fail to read tag of type: " + TagTypes.getType(this.getTagType(key)).getName() + ", since it's corrupt");
		}
		return new byte[0];
	}
	
	public int[] getIntArray(String key) {
		try {
			if (this.contains(key, INT_ARRAY_TAG)) {
				return ((IntArrayTag) this.get(key)).getAsIntArray();
			}
		} catch (ClassCastException e) {
			throw new RuntimeException("Fail to read tag of type: " + TagTypes.getType(this.getTagType(key)).getName() + ", since it's corrupt");
		}
		return new int[0];
	}
	
	public long[] getLongArray(String key) {
		try {
			if (this.contains(key, LONG_ARRAY_TAG)) {
				return ((LongArrayTag) this.get(key)).getAsLongArray();
			}
		} catch (ClassCastException e) {
			throw new RuntimeException("Fail to read tag of type: " + TagTypes.getType(this.getTagType(key)).getName() + ", since it's corrupt");
		}
		return new long[0];
	}
	
	public ListTag getList(String key) {
		return this.getList(key, -1);
	}
	
	public ListTag getList(String key, int type) {
		try {
			if (this.contains(key, LIST_TAG)) {
				ListTag tag = (ListTag) this.get(key);
				if (!tag.isEmpty() && (type == -1 || tag.getElementType() == type)) {
					return tag;
				}
			}
		} catch (ClassCastException e) {
			throw new RuntimeException("Fail to read tag of type: " + TagTypes.getType(this.getTagType(key)).getName() + ", since it's corrupt");
		}
		return new ListTag();
	}
	
	public CompoundTag getCompound(String key) {
		try {
			if (this.contains(key, COMPOUND_TAG)) {
				return (CompoundTag) this.get(key);
			}
		} catch (ClassCastException e) {
			throw new RuntimeException("Fail to read tag of type: " + TagTypes.getType(this.getTagType(key)).getName() + ", since it's corrupt");
		}
		return new CompoundTag();
	}
	
	public boolean getBoolean(String key) {
		return this.getByte(key) != 0;
	}
	
	public void remove(String key) {
		this.data.remove(key);
	}
	
	public boolean isEmpty() {
		return this.data.isEmpty();
	}
	
	@Override
	public String toString() {
		return this.getAsString();
	}
	
	@Override
	public boolean equals(Object object) {
		return Equals.equals(this, object);
	}
	
	@Override
	public int hashCode() {
		return this.data.hashCode();
	}
	
}

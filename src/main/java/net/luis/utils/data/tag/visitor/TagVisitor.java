package net.luis.utils.data.tag.visitor;

import net.luis.utils.data.tag.tags.CompoundTag;
import net.luis.utils.data.tag.tags.EndTag;
import net.luis.utils.data.tag.tags.StringTag;
import net.luis.utils.data.tag.tags.collection.ListTag;
import net.luis.utils.data.tag.tags.collection.array.ByteArrayTag;
import net.luis.utils.data.tag.tags.collection.array.IntArrayTag;
import net.luis.utils.data.tag.tags.collection.array.LongArrayTag;
import net.luis.utils.data.tag.tags.numeric.ByteTag;
import net.luis.utils.data.tag.tags.numeric.DoubleTag;
import net.luis.utils.data.tag.tags.numeric.FloatTag;
import net.luis.utils.data.tag.tags.numeric.IntTag;
import net.luis.utils.data.tag.tags.numeric.LongTag;
import net.luis.utils.data.tag.tags.numeric.ShortTag;

public interface TagVisitor {
	
	void visitByte(ByteTag tag);
	
	void visitShort(ShortTag tag);
	
	void visitInt(IntTag tag);
	
	void visitLong(LongTag tag);
	
	void visitFloat(FloatTag tag);
	
	void visitDouble(DoubleTag tag);
	
	void visitString(StringTag tag);
	
	void visitByteArray(ByteArrayTag tag);
	
	void visitIntArray(IntArrayTag tag);
	
	void visitLongArray(LongArrayTag tag);
	
	void visitList(ListTag tag);
	
	void visitCompound(CompoundTag tag);
	
	void visitEnd(EndTag tag);
	
}
package com.ardublock.translator.block.exception;

import com.ardublock.core.exception.ArdublockException;

public class SubroutineNameDuplicatedException extends ArdublockException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 882306487358983819L;
	
	private Long blockId;

	public Long getBlockId() {
		return blockId;
	}
	
	public SubroutineNameDuplicatedException(Long blockId)
	{
		this.blockId = blockId;
	}

}

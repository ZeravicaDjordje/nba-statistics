package com.mozzartbet.gameservice.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mozzartbet.gameservice.domain.AssistLeaders;
import com.mozzartbet.gameservice.domain.ReboundLeaders;

@Mapper
public interface AssistLeadersMapper extends BaseMapper<AssistLeaders> {

	public int insert(AssistLeaders assistLeaders);

	public long count();
	
	public AssistLeaders save(AssistLeaders assitLeaders);

	public List<AssistLeaders> serachByPlayerUrl(String playerUrl);

	public List<AssistLeaders> searchByGameUrl(String gameUrl);

	public List<AssistLeaders> getAllData();
	
	public List<AssistLeaders> checkForDuplicate(AssistLeaders assistLeaders);
	
	public List<AssistLeaders> getAllId();
	
	public AssistLeaders getById(Long id);//@Param("id") Long id);
	
    public int update(AssistLeaders entity);
 
	public int deleteById(@Param("id") Long id);
	
}

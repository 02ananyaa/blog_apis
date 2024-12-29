package com.blog.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

	
	private Long id;
	private String content;
	private String title;
	private Date addedDate;
	private String imageName;
	
	
	private CategoryDto category;
	private UserDto user;
//	two methods we can use this as well as in url
	
	private Set<CommentDto> comment=new HashSet();
	
}

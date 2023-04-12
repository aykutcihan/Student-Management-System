package com.project.schoolmanagment.payload.request.UpdateRequest;

import com.project.schoolmanagment.payload.request.abstracts.BaseUserRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UpdateTeacherRequest extends BaseUserRequest {

}

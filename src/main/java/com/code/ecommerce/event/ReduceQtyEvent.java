package com.code.ecommerce.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReduceQtyEvent {
    List<ReduceQtyData> reduceQtyDataList;

}

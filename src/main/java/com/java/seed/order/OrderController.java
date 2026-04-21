package com.java.seed.order;

import com.java.seed.shared.Result;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import static com.java.seed.shared.Result.SUCCESS;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    OrderResponse publish(@RequestBody OrderRequest request) {
        orderService.publish(request);
        return new OrderResponse(SUCCESS);
    }

    record OrderRequest(@NotBlank String number){}

    record OrderResponse(@NotBlank Result result){}

}

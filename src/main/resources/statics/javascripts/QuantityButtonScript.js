$(document).ready(function() {
    console.log("Script loaded and ready.");

    function updateTotalPrice() {
        var total = 0;
        $('.quantity').each(function() {
            let $form = $(this);
            let $input = $form.find('input.qty');
            let price = parseFloat($form.closest('tr').find('td:eq(1)').text().replace('$', ''));
            let quantity = parseInt($input.val());
            let totalPrice = price * quantity;
            $form.closest('tr').find('.total-price').text('$' + totalPrice.toFixed(1));
            total += totalPrice;
        });
        $('#total-value').text('$' + total.toFixed(1)); // Update total value
    }

    // Initial check to disable minus buttons where quantity is 1
    $('.quantity').each(function() {
        let $input = $(this).find('input.qty');
        let val = parseInt($input.val());
        if (val <= 1) {
            $(this).find('.minus').prop('disabled', true);
        } else {
            $(this).find('.minus').prop('disabled', false);
        }
    });

    // Plus button logic
    $('.quantity').on('click', '.plus', function(e) {
        e.preventDefault();
        console.log("Plus button clicked.");
        let $form = $(this).closest('form.quantity');
        let $input = $form.find('input.qty');
        let val = parseInt($input.val());
        let productId = $form.find('input[name="productId"]').val();

        $.ajax({
            url: '/cart/add',
            type: 'POST',
            data: { productId: productId, quantity: 1 },
            success: function(response) {
                console.log("Quantity increased");
                $input.val(val + 1).change();
                $form.find('.minus').prop('disabled', false); // Re-enable the minus button
                updateTotalPrice(); // Update total price
            },
            error: function(xhr) {
                console.error("Error increasing quantity", xhr);
            }
        });
    });

    // Minus button logic
    $('.quantity').on('click', '.minus', function(e) {
        e.preventDefault();
        console.log("Minus button clicked.");
        let $form = $(this).closest('form.quantity');
        let $input = $form.find('input.qty');
        let val = parseInt($input.val());
        let productId = $form.find('input[name="productId"]').val();

        if (val > 1) {
            $.ajax({
                url: '/cart/add',
                type: 'POST',
                data: { productId: productId, quantity: -1 },
                success: function(response) {
                    console.log("Quantity decreased");
                    $input.val(val - 1).change();
                    if (val - 1 == 1) {
                        $form.find('.minus').prop('disabled', true); // Disable the minus button if quantity is 1
                    }
                    updateTotalPrice(); // Update total price
                },
                error: function(xhr) {
                    console.error("Error decreasing quantity", xhr);
                }
            });
        }
    });

    // Initial update of total price
    updateTotalPrice();
});

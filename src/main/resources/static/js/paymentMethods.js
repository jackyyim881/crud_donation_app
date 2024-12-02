$(document).ready(function () {
  // Function to fetch and display payment methods in index.html
  function fetchPaymentMethods(filters = {}) {
    $.ajax({
      url: "/api/v1/payment-methods",
      method: "GET",
      data: filters,
      dataType: "json",
      success: function (data) {
        const tableBody = $("#paymentMethodsTableBody");
        tableBody.empty();
        data.forEach((method) => {
          const row = `
                        <tr>
                            <td class="py-2 px-4 border-b">${method.id}</td>
                            <td class="py-2 px-4 border-b">${method.methodName}</td>
                            <td class="py-2 px-4 border-b">
                                <a href="/payment-methods/edit.html?id=${method.id}" class="text-blue-500 hover:text-blue-700">Edit</a>
                                |
                                <a href="#" class="text-red-500 hover:text-red-700 delete-payment-method" data-id="${method.id}">Delete</a>
                            </td>
                        </tr>
                    `;
          tableBody.append(row);
        });
      },
      error: function (err) {
        alert("Error fetching payment methods.");
        console.error(err);
      },
    });
  }

  // Initial fetch on index.html
  if ($("#paymentMethodsTableBody").length) {
    fetchPaymentMethods();
  }

  // Handle Create Payment Method Form Submission
  $("#createPaymentMethodForm").submit(function (e) {
    e.preventDefault();
    const paymentMethodData = {
      methodName: $("#methodName").val().trim(),
    };

    if (paymentMethodData.methodName === "") {
      alert("Method Name is required.");
      return;
    }

    $.ajax({
      url: "/api/v1/payment-methods",
      method: "POST",
      contentType: "application/json",
      data: JSON.stringify(paymentMethodData),
      success: function (data) {
        alert("Payment Method created successfully.");
        window.location.href = "/payment-methods/index.html";
      },
      error: function (err) {
        alert("Error creating payment method.");
        console.error(err);
      },
    });
  });

  // Handle Edit Payment Method Form Population
  function populateEditForm() {
    const urlParams = new URLSearchParams(window.location.search);
    const paymentMethodId = urlParams.get("id");

    if (paymentMethodId) {
      $.ajax({
        url: `/api/v1/payment-methods/${paymentMethodId}`,
        method: "GET",
        dataType: "json",
        success: function (paymentMethod) {
          $("#paymentMethodId").val(paymentMethod.id);
          $("#methodName").val(paymentMethod.methodName);
        },
        error: function (err) {
          alert("Error fetching payment method details.");
          console.error(err);
        },
      });
    } else {
      alert("No payment method ID provided.");
      window.location.href = "/payment-methods/index.html";
    }
  }

  // Call populateEditForm on edit.html
  if ($("#editPaymentMethodForm").length) {
    populateEditForm();
  }

  // Handle Edit Payment Method Form Submission
  $("#editPaymentMethodForm").submit(function (e) {
    e.preventDefault();
    const paymentMethodId = $("#paymentMethodId").val();
    const paymentMethodData = {
      methodName: $("#methodName").val().trim(),
    };

    if (paymentMethodData.methodName === "") {
      alert("Method Name is required.");
      return;
    }

    $.ajax({
      url: `/api/v1/payment-methods/${paymentMethodId}`,
      method: "PUT",
      contentType: "application/json",
      data: JSON.stringify(paymentMethodData),
      success: function (data) {
        alert("Payment Method updated successfully.");
        window.location.href = "/payment-methods/index.html";
      },
      error: function (err) {
        alert("Error updating payment method.");
        console.error(err);
      },
    });
  });

  // Handle Delete Payment Method
  $(document).on("click", ".delete-payment-method", function (e) {
    e.preventDefault();
    const paymentMethodId = $(this).data("id");
    if (confirm("Are you sure you want to delete this payment method?")) {
      $.ajax({
        url: `/api/v1/payment-methods/${paymentMethodId}`,
        method: "DELETE",
        success: function () {
          alert("Payment Method deleted successfully.");
          fetchPaymentMethods();
        },
        error: function (err) {
          alert("Error deleting payment method.");
          console.error(err);
        },
      });
    }
  });

  // Handle Filter Form Submission
  $("#filterForm").submit(function (e) {
    e.preventDefault();
    const filters = {
      methodName: $("#methodName").val().trim(),
    };
    fetchPaymentMethods(filters);
  });

  // Handle Reset Filters
  $("#resetFilters").click(function () {
    $("#filterForm")[0].reset();
    fetchPaymentMethods();
  });
});

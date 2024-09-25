$(document).ready(function () {
  // Function to fetch and display receipts in index.html
  function fetchReceipts() {
    $.ajax({
      url: "/api/v1/receipts",
      method: "GET",
      dataType: "json",
      success: function (data) {
        const tableBody = $("#receiptsTableBody");
        tableBody.empty();
        data.forEach((receipt) => {
          const row = `
                        <tr>
                            <td class="py-2 px-4 border-b">${receipt.id}</td>
                            <td class="py-2 px-4 border-b">${
                              receipt.receiptNumber
                            }</td>
                            <td class="py-2 px-4 border-b">${
                              receipt.donation.donor.user.username
                            }</td>
                            <td class="py-2 px-4 border-b">${
                              receipt.donation.donationDate
                            }</td>
                            <td class="py-2 px-4 border-b">${
                              receipt.donation.ncampaign.name || "N/A"
                            }</td>
                            <td class="py-2 px-4 border-b">${
                              receipt.donation.paymentMethod.methodName || "N/A"
                            }</td>
                            <td class="py-2 px-4 border-b">
                                <a href="/receipts/edit.html?id=${
                                  receipt.id
                                }" class="text-blue-500 hover:text-blue-700">Edit</a>
                                |
                                <a href="#" class="text-red-500 hover:text-red-700 delete-receipt" data-id="${
                                  receipt.id
                                }">Delete</a>
                            </td>
                        </tr>
                    `;
          tableBody.append(row);
        });
      },
      error: function (err) {
        alert("Error fetching receipts.");
        console.error(err);
      },
    });
  }

  // Initial fetch on index.html
  if ($("#receiptsTableBody").length) {
    fetchReceipts();
  }

  function populateDropdown(url, selectId) {
    $.ajax({
      url: url,
      method: "GET",
      dataType: "json",
      success: function (data) {
        const select = $(`#${selectId}`);
        select.empty();
        select.append('<option value="">Select an option</option>');
        data.forEach((item) => {
          const option = `<option value="${item.id}">${
            item.name || item.username
          }</option>`;
          select.append(option);
        });
      },
      error: function (err) {
        console.error(`Error fetching data for ${selectId}:`, err);
      },
    });
  }
  // Populate dropdowns for create.html and edit.html
  if ($("#createReceiptForm").length || $("#editReceiptForm").length) {
    const donorsPromise = populateDropdown(
      "/api/v1/donors",
      "donorId",
      "username"
    );
    const campaignsPromise = populateDropdown(
      "/api/v1/campaigns",
      "campaignId",
      "name"
    );
    const paymentMethodsPromise = populateDropdown(
      "/api/v1/payment-methods",
      "paymentMethodId",
      "methodName"
    );

    // Wait for all dropdowns to be populated before populating the edit form
    $.when(donorsPromise, campaignsPromise, paymentMethodsPromise).done(
      function () {
        if ($("#editReceiptForm").length) {
          populateEditForm();
        }
      }
    );
  }
  // Handle Create Receipt Form Submission
  $("#createReceiptForm").submit(function (e) {
    e.preventDefault();
    const receiptData = {
      amount: parseFloat($("#amount").val()),
      donorId: parseInt($("#donorId").val()),
      campaignId: parseInt($("#campaignId").val()),
      paymentMethodId: parseInt($("#paymentMethodId").val()),
      donationDate: $("#donationDate").val(),
      note: $("#note").val(),
    };

    $.ajax({
      url: "/api/v1/receipts",
      method: "POST",
      contentType: "application/json",
      data: JSON.stringify(receiptData),
      success: function (data) {
        alert("Receipt created successfully.");
        window.location.href = "/receipts/index.html";
      },
      error: function (err) {
        alert("Error creating receipt.");
        console.error(err);
      },
    });
  });

  // Handle Edit Receipt Form Population
  function populateEditForm() {
    const urlParams = new URLSearchParams(window.location.search);
    const receiptId = urlParams.get("id");

    if (receiptId) {
      $.ajax({
        url: `/api/v1/receipts/${receiptId}`,
        method: "GET",
        dataType: "json",
        success: function (receipt) {
          $("#receiptNumber").val(receipt.receiptNumber);
          $("#issuedDate").val(receipt.issuedDate);
          $("#receiptId").val(receipt.id);
          $("#amount").val(receipt.donation.amount);
          $("#donorId").val(receipt.donation.donor.id);
          $("#campaignId").val(receipt.donation.ncampaign.id);
          $("#paymentMethodId").val(receipt.donation.paymentMethod.id);
          $("#donationDate").val(receipt.donation.donationDate);
          $("#note").val(receipt.note || "");
        },
        error: function (err) {
          alert("Error fetching receipt details.");
          console.error(err);
        },
      });
    } else {
      alert("No receipt ID provided.");
      window.location.href = "/receipts/index.html";
    }
  }

  // Call populateEditForm on edit.html
  if ($("#editReceiptForm").length) {
    populateEditForm();
  }

  // Handle Edit Receipt Form Submission
  $("#editReceiptForm").submit(function (e) {
    e.preventDefault();
    const receiptId = $("#receiptId").val();
    const receiptData = {
      amount: parseFloat($("#amount").val()),
      donorId: parseInt($("#donorId").val()),
      campaignId: parseInt($("#campaignId").val()),
      paymentMethodId: parseInt($("#paymentMethodId").val()),
      donationDate: $("#donationDate").val(),
      note: $("#note").val(),
    };

    $.ajax({
      url: `/api/v1/receipts/${receiptId}`,
      method: "PUT",
      contentType: "application/json",
      data: JSON.stringify(receiptData),
      success: function (data) {
        alert("Receipt updated successfully.");
        window.location.href = "/receipts/index.html";
      },
      error: function (err) {
        alert("Error updating receipt.");
        console.error(err);
      },
    });
  });

  // Handle Delete Receipt
  $(document).on("click", ".delete-receipt", function (e) {
    e.preventDefault();
    const receiptId = $(this).data("id");
    if (confirm("Are you sure you want to delete this receipt?")) {
      $.ajax({
        url: `/api/v1/receipts/${receiptId}`,
        method: "DELETE",
        success: function () {
          alert("Receipt deleted successfully.");
          fetchReceipts();
        },
        error: function (err) {
          alert("Error deleting receipt.");
          console.error(err);
        },
      });
    }
  });
});

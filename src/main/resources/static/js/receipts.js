$(document).ready(function () {
  // Entry point
  if ($("#receiptsTableBody").length) {
    fetchReceipts();
  }

  if ($("#createReceiptForm").length || $("#editReceiptForm").length) {
    populateDropdowns();
  }

  if ($("#editReceiptForm").length) {
    populateEditForm();
  }

  // Event Listeners
  $(document).on("click", ".download-receipt", handleDownloadReceipt);
  $(document).on("click", ".delete-receipt", handleDeleteReceipt);
  $("#createReceiptForm").on("submit", handleCreateReceipt);
  $("#editReceiptForm").on("submit", handleEditReceipt);
});

// Utility Functions
function ajaxRequest(url, method, data = null, dataType = "json") {
  return $.ajax({
    url,
    method,
    data,
    dataType,
    contentType: "application/json",
    xhrFields: dataType === "blob" ? { responseType: "blob" } : undefined,
  });
}

// Receipt Functions
function fetchReceipts() {
  ajaxRequest("/api/v1/receipts", "GET")
    .done(displayReceipts)
    .fail(handleAjaxError("Error fetching receipts."));
}

function displayReceipts(data) {
  const tableBody = $("#receiptsTableBody");
  tableBody.empty();
  data.forEach((receipt) => {
    const row = `
      <tr class="bg-white dark:bg-gray-800">
        <td class="py-2 px-4 border-b border-gray-200 dark:border-gray-700 text-gray-900 dark:text-gray-100">${
          receipt.id
        }</td>
        <td class="py-2 px-4 border-b border-gray-200 dark:border-gray-700 text-gray-900 dark:text-gray-100">${
          receipt.receiptNumber
        }</td>
        <td class="py-2 px-4 border-b border-gray-200 dark:border-gray-700 text-gray-900 dark:text-gray-100">${
          receipt.donorUsername
        }</td>
        <td class="py-2 px-4 border-b border-gray-200 dark:border-gray-700 text-gray-900 dark:text-gray-100">${
          receipt.donationDate
        }</td>
        <td class="py-2 px-4 border-b border-gray-200 dark:border-gray-700 text-gray-900 dark:text-gray-100">${
          receipt.campaignName || "N/A"
        }</td>
        <td class="py-2 px-4 border-b border-gray-200 dark:border-gray-700 text-gray-900 dark:text-gray-100">${
          receipt.paymentMethodName || "N/A"
        }</td>
        <td class="py-2 px-4 border-b border-gray-200 dark:border-gray-700 text-gray-900 dark:text-gray-100">
          <a href="/receipts/edit.html?id=${
            receipt.id
          }" class="text-blue-500 dark:text-blue-400 hover:text-blue-700 dark:hover:text-blue-300">Edit</a> |
          <a href="#" class="text-red-500 dark:text-red-400 hover:text-red-700 dark:hover:text-red-300 delete-receipt" data-id="${
            receipt.id
          }">Delete</a> |
          <a href="#" class="text-green-500 dark:text-green-400 hover:text-green-700 dark:hover:text-green-300 download-receipt" data-id="${
            receipt.id
          }">Download PDF</a>
        </td>
      </tr>`;
    tableBody.append(row);
  });
}

function handleDownloadReceipt(e) {
  e.preventDefault();
  const receiptId = $(this).data("id");

  // Make an AJAX request to get the PDF and trigger the download
  $.ajax({
    url: `/downloadReceipt/${receiptId}`,
    method: "GET",
    xhrFields: {
      responseType: "blob",
    },
    success: function (data) {
      const blob = new Blob([data], { type: "application/pdf" });
      const link = document.createElement("a");
      link.href = window.URL.createObjectURL(blob);
      link.download = `receipt_${receiptId}.pdf`;
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
    },
    error: function (err) {
      alert("Error downloading receipt.");
      console.error(err);
    },
  });
}

function handleDeleteReceipt(e) {
  e.preventDefault();
  const receiptId = $(this).data("id");
  if (confirm("Are you sure you want to delete this receipt?")) {
    ajaxRequest(`/api/v1/receipts/${receiptId}`, "DELETE")
      .done(() => {
        alert("Receipt deleted successfully.");
        fetchReceipts();
      })
      .fail(handleAjaxError("Error deleting receipt."));
  }
}

// Form Functions
function populateDropdowns() {
  $.when(
    populateDropdown("/api/v1/donors", "donorId"),
    populateDropdown("/api/v1/campaigns", "campaignId"),
    populateDropdown("/api/v1/payment-methods", "paymentMethodId")
  ).done(function () {
    if ($("#editReceiptForm").length) {
      populateEditForm();
    }
  });
}

function populateDropdown(url, selectId) {
  return ajaxRequest(url, "GET")
    .done((data) => {
      const select = $(`#${selectId}`);
      select.empty().append('<option value="">Select an option</option>');
      data.forEach((item) => {
        const option = `<option value="${item.id}">${
          item.name || item.username
        }</option>`;
        select.append(option);
      });
    })
    .fail(handleAjaxError(`Error fetching data for ${selectId}.`));
}

function handleCreateReceipt(e) {
  e.preventDefault();
  const receiptData = getFormData();

  ajaxRequest("/api/v1/receipts", "POST", JSON.stringify(receiptData))
    .done(() => {
      alert("Receipt created successfully.");
      window.location.href = "/receipts/index.html";
    })
    .fail(handleAjaxError("Error creating receipt."));
}

function handleEditReceipt(e) {
  e.preventDefault();
  const receiptId = $("#receiptId").val();
  const receiptData = getFormData();

  ajaxRequest(
    `/api/v1/receipts/${receiptId}`,
    "PUT",
    JSON.stringify(receiptData)
  )
    .done(() => {
      alert("Receipt updated successfully.");
      window.location.href = "/receipts/index.html";
    })
    .fail(handleAjaxError("Error updating receipt."));
}

function populateEditForm() {
  const urlParams = new URLSearchParams(window.location.search);
  const receiptId = urlParams.get("id");

  if (receiptId) {
    ajaxRequest(`/api/v1/receipts/${receiptId}`, "GET")
      .done((receipt) => {
        $("#receiptNumber").val(receipt.receiptNumber);
        $("#issuedDate").val(receipt.issuedDate);
        $("#receiptId").val(receipt.id);
        $("#amount").val(receipt.donation.amount);
        $("#donorId").val(receipt.donation.donor.id);
        $("#campaignId").val(receipt.donation.campaign.id);
        $("#paymentMethodId").val(receipt.donation.paymentMethod.id);
        $("#donationDate").val(receipt.donation.donationDate);
        $("#note").val(receipt.note || "");
      })
      .fail(handleAjaxError("Error fetching receipt details."));
  } else {
    alert("No receipt ID provided.");
    window.location.href = "/receipts/index.html";
  }
}

// Helper Functions
function getFormData() {
  return {
    amount: parseFloat($("#amount").val()),
    donorId: parseInt($("#donorId").val()),
    campaignId: parseInt($("#campaignId").val()),
    paymentMethodId: parseInt($("#paymentMethodId").val()),
    donationDate: $("#donationDate").val(),
    note: $("#note").val(),
  };
}

function handleAjaxError(message) {
  return function (err) {
    alert(message);
    console.error(err);
  };
}

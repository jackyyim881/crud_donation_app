$(document).ready(function () {
  // Function to fetch and display donations in index.html
  function fetchDonations(filters = {}) {
    $.ajax({
      url: "/api/v1/donations",
      method: "GET",
      data: filters,
      dataType: "json",
      success: function (data) {
        const tableBody = $("#donationsTableBody");
        tableBody.empty();
        data.forEach((donation) => {
          const row = `
                        <tr>
                            <td class="py-2 px-4 border-b">${donation.id}</td>
                            <td class="py-2 px-4 border-b">${
                              donation.amount
                            }</td>
                            <td class="py-2 px-4 border-b">${
                              donation.donorName
                            }</td>
                            <td class="py-2 px-4 border-b">${
                              donation.donationDate
                            }</td>
                            <td class="py-2 px-4 border-b">${
                              donation.campaignName || "N/A"
                            }</td>
                            <td class="py-2 px-4 border-b">${
                              donation.paymentMethodName || "N/A"
                            }</td>
                            <td class="py-2 px-4 border-b">
                                <a href="/donations/edit?id=${
                                  donation.id
                                }" class="text-blue-500 hover:text-blue-700">Edit</a>
                                |
                                <a href="#" class="text-red-500 hover:text-red-700 delete-donation" data-id="${
                                  donation.id
                                }">Delete</a>
                            </td>
                        </tr>
                    `;
          tableBody.append(row);
        });
      },
      error: function (err) {
        alert("Error fetching donations.");
        console.error(err);
      },
    });
  }

  // Initial fetch on index.html
  if ($("#donationsTableBody").length) {
    fetchDonations();
  }

  // Function to populate dropdowns (Donors, Campaigns, Payment Methods)
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
  if ($("#createDonationForm").length || $("#editDonationForm").length) {
    populateDropdown("/api/v1/donors", "donorId");
    populateDropdown("/api/v1/campaigns", "campaignId");
    populateDropdown("/api/v1/payment-methods", "paymentMethodId");
  }

  // Handle Create Donation Form Submission
  $("#createDonationForm").submit(function (e) {
    e.preventDefault();
    const donationData = {
      amount: parseFloat($("#amount").val()),
      donorId: parseInt($("#donorId").val()),
      campaignId: parseInt($("#campaignId").val()),
      paymentMethodId: parseInt($("#paymentMethodId").val()),
      donationDate: $("#donationDate").val(),
      note: $("#note").val(),
    };

    $.ajax({
      url: "/api/v1/donations",
      method: "POST",
      contentType: "application/json",
      data: JSON.stringify(donationData),
      success: function (data) {
        alert("Donation created successfully.");
        window.location.href = "/donations/index.html";
      },
      error: function (err) {
        alert("Error creating donation.");
        console.error(err);
      },
    });
  });

  // Handle Edit Donation Form Population
  function populateEditForm() {
    const urlParams = new URLSearchParams(window.location.search);
    const donationId = urlParams.get("id");

    if (donationId) {
      $.ajax({
        url: `/api/v1/donations/${donationId}`,
        method: "GET",
        dataType: "json",
        success: function (donation) {
          $("#donationId").val(donation.id);
          $("#amount").val(donation.amount);
          $("#donorId").val(donation.donorId);
          $("#campaignId").val(donation.campaignId);
          $("#paymentMethodId").val(donation.paymentMethodId);
          $("#donationDate").val(donation.donationDate);
          $("#note").val(donation.note || "");
        },
        error: function (err) {
          alert("Error fetching donation details.");
          console.error(err);
        },
      });
    } else {
      alert("No donation ID provided.");
      window.location.href = "/donations/index.html";
    }
  }

  // Call populateEditForm on edit.html
  if ($("#editDonationForm").length) {
    populateEditForm();
  }

  // Handle Edit Donation Form Submission
  $("#editDonationForm").submit(function (e) {
    e.preventDefault();
    const donationId = $("#donationId").val();
    const donationData = {
      amount: parseFloat($("#amount").val()),
      donorId: parseInt($("#donorId").val()),
      campaignId: parseInt($("#campaignId").val()),
      paymentMethodId: parseInt($("#paymentMethodId").val()),
      donationDate: $("#donationDate").val(),
      note: $("#note").val(),
    };

    $.ajax({
      url: `/api/v1/donations/${donationId}`,
      method: "PUT",
      contentType: "application/json",
      data: JSON.stringify(donationData),
      success: function (data) {
        alert("Donation updated successfully.");
        window.location.href = "/donations/index.html";
      },
      error: function (err) {
        alert("Error updating donation.");
        console.error(err);
      },
    });
  });

  // Handle Delete Donation
  $(document).on("click", ".delete-donation", function (e) {
    e.preventDefault();
    const donationId = $(this).data("id");
    if (confirm("Are you sure you want to delete this donation?")) {
      $.ajax({
        url: `/api/v1/donations/${donationId}`,
        method: "DELETE",
        success: function () {
          alert("Donation deleted successfully.");
          fetchDonations();
        },
        error: function (err) {
          alert("Error deleting donation.");
          console.error(err);
        },
      });
    }
  });

  // Handle Filter Form Submission
  $("#filterForm").submit(function (e) {
    e.preventDefault();
    const filters = {
      minAmount: $("#minAmount").val(),
      maxAmount: $("#maxAmount").val(),
      startDate: $("#startDate").val(),
      endDate: $("#endDate").val(),
      donorId: $("#donorId").val(),
    };
    fetchDonations(filters);
  });

  // Handle Reset Filters
  $("#resetFilters").click(function () {
    $("#filterForm")[0].reset();
    fetchDonations();
  });
});

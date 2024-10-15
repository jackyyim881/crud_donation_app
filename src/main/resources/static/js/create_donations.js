$(document).ready(function () {
  // Load initial data
  populateCampaigns();
  populatePaymentMethods();
  populateDonor(); // Auto-populate donor field for the current user
  loadDonations(); // Load all donations to display in the table

  function createDonation() {
    // Gather form data
    let donation = {
      amount: $("#amount").val(),
      donorId: $("#donorId").val(), // This will be auto-populated
      campaignId: $("#campaignId").val(),
      paymentMethodId: $("#paymentMethodId").val(),
      donationDate: new Date().toISOString(), // Assuming the current date
    };

    // Make AJAX request to create a donation
    $.ajax({
      url: "/api/donations/create",
      type: "POST",
      contentType: "application/json",
      data: JSON.stringify(donation),
      success: function (response) {
        alert("Donation created successfully!");
        location.reload(); // Reload the page to see updated donations list
      },
      error: function (error) {
        console.error("Error creating donation:", error);
        alert("Failed to create donation. Please try again.");
      },
    });
  }

  function loadDonations() {
    $.ajax({
      url: "/api/donations",
      type: "GET",
      success: function (donations) {
        renderDonations(donations);
      },
      error: function (error) {
        console.error("Error fetching donations:", error);
        alert("Failed to fetch donations. Please try again.");
      },
    });
  }

  function populateCampaigns() {
    $.ajax({
      url: "/api/v1/campaigns",
      type: "GET",
      success: function (data) {
        data.forEach((campaign) => {
          $("#campaignId").append(
            `<option value="${campaign.id}">${campaign.name}</option>`
          );
        });
      },
      error: function (error) {
        console.error("Error fetching campaigns:", error);
        alert("Failed to fetch campaigns. Please try again.");
      },
    });
  }

  function populatePaymentMethods() {
    $.ajax({
      url: "/api/v1/payment-methods",
      type: "GET",
      success: function (data) {
        data.forEach((paymentMethod) => {
          $("#paymentMethodId").append(
            `<option value="${paymentMethod.id}">${paymentMethod.methodName}</option>`
          );
        });
      },
      error: function (error) {
        console.error("Error fetching payment methods:", error);
        alert("Failed to fetch payment methods. Please try again.");
      },
    });
  }

  function populateDonor() {
    $.ajax({
      url: "/api/v1/donors/current",
      type: "GET",
      success: function (data) {
        if (data) {
          $("#donorId").val(data.id);
          $("#donorInfo").text(
            `Donor: ${data.user.username} (Type: ${data.donorType})`
          );
        } else {
          console.error("No donor found for the current user.");
          alert("You must be registered as a donor to create a donation.");
        }
      },
      error: function (error) {
        console.error("Error fetching current user donor information:", error);
        alert("Failed to fetch donor information. Please try again.");
      },
    });
  }
});

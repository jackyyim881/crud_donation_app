document.addEventListener("DOMContentLoaded", function () {
  var calendarEl = document.getElementById("calendar");
  var yearlyCalendarEl = document.getElementById("yearlyCalendar");
  var tooltipEl = document.getElementById("tooltip");
  var notificationEl = document.getElementById("notification");
  var loadingSpinner = document.getElementById("loadingSpinner");
  var searchInput = document.getElementById("searchEvents");

  // Initialize FullCalendar with default view set to week
  var calendar = new FullCalendar.Calendar(calendarEl, {
    initialView: "timeGridWeek",
    headerToolbar: false,
    editable: true,
    selectable: true,
    selectMirror: true,
    dayMaxEvents: true,
    events: `/api/v1/events?userId=${currentUserId}`, // Fetch events from backend
    eventColor: "#4F46E5", // Indigo-600
    nowIndicator: true, // Shows current time indicator
    initialDate: new Date(), // Set to current date
    loading: function (isLoading) {
      if (isLoading) {
        loadingSpinner.classList.remove("hidden");
      } else {
        loadingSpinner.classList.add("hidden");
      }
    },
    datesSet: function (dateInfo) {
      updateWeeklyDateRange(dateInfo.start, dateInfo.end);
      highlightCurrentWeek(dateInfo.start, dateInfo.end);
    },
    eventClick: function (info) {
      // Display tooltip with event details
      showTooltip(info.event, info.jsEvent);
    },
    eventMouseEnter: function (info) {
      // Optionally, implement hover effects
    },
    eventMouseLeave: function (info) {
      // Hide tooltip
      hideTooltip();
    },
    eventDrop: function (info) {
      // Handle event drop (drag-and-drop)
      updateEvent(info.event);
    },
    eventResize: function (info) {
      // Handle event resize
      updateEvent(info.event);
    },
  });

  calendar.render();

  // Function to update the active tab button style
  function updateActiveTab(activeButtonId) {
    const tabButtons = document.querySelectorAll(".nav-tab");
    tabButtons.forEach((button) => {
      if (button.id === activeButtonId) {
        button.classList.remove(
          "text-gray-700",
          "dark:text-gray-300",
          "border-transparent"
        );
        button.classList.add(
          "text-indigo-600",
          "dark:text-indigo-400",
          "border-indigo-600"
        );
      } else {
        button.classList.remove(
          "text-indigo-600",
          "dark:text-indigo-400",
          "border-indigo-600"
        );
        button.classList.add(
          "text-gray-700",
          "dark:text-gray-300",
          "border-transparent"
        );
      }
    });
  }

  // Function to format date to a readable string
  function formatDate(date) {
    const options = {
      month: "short",
      day: "numeric",
    };
    return date.toLocaleDateString(undefined, options);
  }

  // Function to update the weekly date range display
  function updateWeeklyDateRange(start, end) {
    const startDate = new Date(start);
    const endDate = new Date(end);
    // Adjust endDate to the last day of the week
    endDate.setDate(endDate.getDate() - 1); // FullCalendar's end date is exclusive

    const formattedStart = formatDate(startDate);
    const formattedEnd = formatDate(endDate);

    document.getElementById(
      "weeklyDateRange"
    ).textContent = `${formattedStart} - ${formattedEnd}`;
  }

  // Function to highlight the current week
  function highlightCurrentWeek(start, end) {
    // Remove existing highlights
    const existingHighlights = document.querySelectorAll(
      ".fc-highlighted-week"
    );
    existingHighlights.forEach((el) =>
      el.classList.remove("fc-highlighted-week")
    );

    // Get all days in the current week
    let current = new Date(start);
    while (current < end) {
      const dateStr = current.toISOString().split("T")[0];
      const dayEl = calendarEl.querySelector(`[data-date="${dateStr}"]`);
      if (dayEl) {
        dayEl.classList.add("fc-highlighted-week");
      }
      current.setDate(current.getDate() + 1);
    }
  }

  // Function to initialize Yearly View
  function initializeYearlyView(year) {
    yearlyCalendarEl.innerHTML = ""; // Clear existing calendars
    const months = [
      "January",
      "February",
      "March",
      "April",
      "May",
      "June",
      "July",
      "August",
      "September",
      "October",
      "November",
      "December",
    ];

    months.forEach((month, index) => {
      // Create a container for each month
      var monthContainer = document.createElement("div");
      monthContainer.classList.add(
        "rounded-lg",
        "shadow-md",
        "border",
        "border-gray-200",
        "dark:border-gray-700",
        "bg-white",
        "dark:bg-gray-800",
        "p-2"
      );

      // Create a header for the month
      var monthHeader = document.createElement("h3");
      monthHeader.classList.add(
        "text-center",
        "font-semibold",
        "mb-2",
        "text-gray-700",
        "dark:text-gray-300"
      );
      monthHeader.textContent = `${month} ${year}`;
      monthContainer.appendChild(monthHeader);

      // Create a calendar element for the month
      var monthCalendarEl = document.createElement("div");
      monthCalendarEl.classList.add("h-64", "w-full");
      monthCalendarEl.id = `calendar-${index}`; // Unique ID for each calendar
      monthContainer.appendChild(monthCalendarEl);

      yearlyCalendarEl.appendChild(monthContainer);

      // Initialize FullCalendar for the month
      var monthCalendar = new FullCalendar.Calendar(monthCalendarEl, {
        initialView: "dayGridMonth",
        headerToolbar: {
          left: "",
          center: "title",
          right: "",
        },
        allDaySlot: false,
        editable: false,
        selectable: false,
        dayMaxEvents: true,
        events: "/api/v1/events", // Fetch events from backend
        eventColor: "#4F46E5",
        initialDate: new Date(year, index, 1),
        height: "auto",
        // Disable interaction
        selectable: false,
        selectMirror: false,
        select: function (info) {},
        eventClick: function (info) {
          // Display tooltip with event details
          showTooltip(info.event, info.jsEvent);
        },
        eventMouseEnter: function (info) {
          // Optionally, implement hover effects
        },
        eventMouseLeave: function (info) {
          // Hide tooltip
          hideTooltip();
        },
      });

      monthCalendar.render();
    });
  }

  // Event listeners for tab buttons
  document.getElementById("weeklyView").addEventListener("click", function () {
    // Show Weekly Calendar, hide Yearly
    calendarEl.classList.remove("hidden");
    yearlyCalendarEl.classList.add("hidden");
    updateActiveTab("weeklyView");
    calendar.changeView("timeGridWeek");
    searchInput.value = ""; // Clear search input
  });

  document.getElementById("monthlyView").addEventListener("click", function () {
    // Show Monthly Calendar, hide Yearly
    calendarEl.classList.remove("hidden");
    yearlyCalendarEl.classList.add("hidden");
    updateActiveTab("monthlyView");
    calendar.changeView("dayGridMonth");
    document.getElementById("weeklyDateRange").textContent = ""; // Clear date range
    searchInput.value = ""; // Clear search input
  });

  document.getElementById("yearlyView").addEventListener("click", function () {
    // Hide Weekly and Monthly Calendars, show Yearly
    calendarEl.classList.add("hidden");
    yearlyCalendarEl.classList.remove("hidden");
    updateActiveTab("yearlyView");

    // Initialize Yearly View for the current year
    var currentYear = new Date().getFullYear();
    initializeYearlyView(currentYear);
    searchInput.value = ""; // Clear search input
  });

  // Handle navigation buttons for Weekly view
  document.getElementById("prevWeek").addEventListener("click", function () {
    calendar.prev(); // Navigate to previous week
  });

  document.getElementById("nextWeek").addEventListener("click", function () {
    calendar.next(); // Navigate to next week
  });

  // Initially set the active tab
  updateActiveTab("weeklyView");

  // Handle Create Schedule Button
  const createScheduleBtn = document.getElementById("create-schedule-btn");
  const scheduleModal = document.getElementById("scheduleModal");
  const cancelScheduleBtn = document.getElementById("cancelScheduleBtn");
  const scheduleForm = document.getElementById("scheduleForm");

  createScheduleBtn.addEventListener("click", () => {
    scheduleModal.classList.remove("hidden");
    // Pre-fill start date if in weekly view
    if (!calendarEl.classList.contains("hidden")) {
      var view = calendar.view;
      var selectedDate = view.currentStart;
      document.getElementById("eventStart").value =
        moment(selectedDate).format("YYYY-MM-DDTHH:mm");
      document.getElementById("eventEnd").value = moment(selectedDate)
        .add(1, "hours")
        .format("YYYY-MM-DDTHH:mm");
    }
  });

  cancelScheduleBtn.addEventListener("click", () => {
    scheduleModal.classList.add("hidden");
    scheduleForm.reset();
  });

  // Handle Schedule Form Submission
  scheduleForm.addEventListener("submit", function (e) {
    e.preventDefault();
    if (!currentUserId) {
      console.error("User ID is not defined");
      return;
    }
    const formData = {
      title: document.getElementById("eventTitle").value,
      description: document.getElementById("eventDescription").value,
      start: document.getElementById("eventStart").value,
      end: document.getElementById("eventEnd").value,
      category: document.getElementById("eventCategory").value,
      color: document.getElementById("eventColor").value,
      userId: currentUserId, // Add userId to formData
    };

    // Basic validation
    if (new Date(formData.start) >= new Date(formData.end)) {
      alert("End date must be after start date.");
      return;
    }

    // Show loading spinner
    loadingSpinner.classList.remove("hidden");
    axios.get("/api/v1/events").then((response) => {
      console.log(response.data);
    });
    // Send POST request to backend to create event
    if (!currentUserId) {
      console.error("User ID is not defined");
      return;
    }
    axios
      .post("/api/v1/events", formData)
      .then((response) => {
        // Add event to calendar if in Weekly or Monthly view
        if (!calendarEl.classList.contains("hidden")) {
          calendar.addEvent({
            title: response.data.title,
            description: response.data.description,
            start: response.data.start,
            end: response.data.end,
            color: response.data.color,
          });
        } else {
          // For Yearly view, reload all month calendars
          initializeYearlyView(new Date().getFullYear());
        }
        // Show success notification
        showNotification("Event created successfully!");
        // Close modal and reset form
        scheduleModal.classList.add("hidden");
        scheduleForm.reset();
      })
      .catch((error) => {
        console.error("There was an error creating the event!", error);
        alert("Failed to create event. Please try again.");
      })
      .finally(() => {
        // Hide loading spinner
        loadingSpinner.classList.add("hidden");
      });
  });

  // Function to show tooltip
  function showTooltip(event, jsEvent) {
    tooltipEl.innerHTML = `
            <strong>${event.title}</strong><br/>
            ${event.extendedProps.description || ""}
            <br/>
            ${moment(event.start).format("LLL")} - ${moment(event.end).format(
      "LLL"
    )}
          `;
    tooltipEl.style.top = jsEvent.pageY + 10 + "px";
    tooltipEl.style.left = jsEvent.pageX + 10 + "px";
    tooltipEl.classList.remove("hidden");
  }

  // Function to hide tooltip
  function hideTooltip() {
    tooltipEl.classList.add("hidden");
  }

  // Function to show notification
  function showNotification(message) {
    notificationEl.textContent = message;
    notificationEl.classList.remove("hidden");
    setTimeout(() => {
      notificationEl.classList.add("hidden");
    }, 3000);
  }

  // Function to update event after drag-and-drop or resize
  function updateEvent(event) {
    // Show loading spinner
    loadingSpinner.classList.remove("hidden");

    // Prepare updated event data
    const updatedEvent = {
      id: event.id,
      title: event.title,
      description: event.extendedProps.description,
      start: event.start.toISOString(),
      end: event.end ? event.end.toISOString() : null,
      category: event.extendedProps.category,
      color: event.backgroundColor,
    };

    // Send PUT request to backend to update event
    axios
      .put(`/api/v1/events/${event.id}`, updatedEvent)
      .then((response) => {
        showNotification("Event updated successfully!");
      })
      .catch((error) => {
        console.error("There was an error updating the event!", error);
        alert("Failed to update event. Please try again.");
        event.revert();
      })
      .finally(() => {
        // Hide loading spinner
        loadingSpinner.classList.add("hidden");
      });
  }

  // Handle Sidebar Toggle for Mobile
  document
    .getElementById("sidebarToggle")
    .addEventListener("click", function () {
      const sidebar = document.querySelector(".sidebar");
      sidebar.classList.toggle("hidden");
    });

  // Implement Search Functionality
  searchInput.addEventListener("input", function (e) {
    const query = e.target.value.toLowerCase();
    calendar.getEvents().forEach((event) => {
      const matches =
        event.title.toLowerCase().includes(query) ||
        (event.extendedProps.description &&
          event.extendedProps.description.toLowerCase().includes(query));
      if (matches) {
        event.setProp("display", "auto");
      } else {
        event.setProp("display", "none");
      }
    });

    // Also filter in Yearly View if visible
    if (!yearlyCalendarEl.classList.contains("hidden")) {
      const monthCalendars =
        yearlyCalendarEl.querySelectorAll("[id^='calendar-']");
      monthCalendars.forEach((monthCalEl) => {
        var monthCal = FullCalendar.getCalendar(monthCalEl);
        if (monthCal) {
          monthCal.getEvents().forEach((event) => {
            const matches =
              event.title.toLowerCase().includes(query) ||
              (event.extendedProps.description &&
                event.extendedProps.description.toLowerCase().includes(query));
            if (matches) {
              event.setProp("display", "auto");
            } else {
              event.setProp("display", "none");
            }
          });
        }
      });
    }
  });

  // Accessibility Enhancements: Close modal with Escape key
  document.addEventListener("keydown", function (e) {
    if (e.key === "Escape" && !scheduleModal.classList.contains("hidden")) {
      scheduleModal.classList.add("hidden");
      scheduleForm.reset();
    }
  });
});

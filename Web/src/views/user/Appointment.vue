<template>
    <div id="appointment" class="appointment-wrapper">
        <a-select
            ref="select"
            v-model:value="selected"
            style="width: 120px"
            @focus="focus"
            @change="handleChangeView"
            class="select-view"
        >
            <a-select-option value="day">Day</a-select-option>
            <a-select-option value="week">Week</a-select-option>
            <a-select-option value="month">Month</a-select-option>
        </a-select>

        <div class="calendar-container">
            <vue-cal
                ref="vuecal"
                small
                v-model:active-view="activeView"
                hide-view-selector
                :selected-date="currentDate"
                :time-from="6 * 60"
                :time-to="23 * 60"
                :watchRealTime="true"
                :disable-views="['years', 'year']"
                editable-events
                :hide-weekends="false"
                events-on-month-view="short"
                events-count-on-year-view
                :min-event-width="minEventWidth"
                :events="events"
                class="calendar-scrollable"
                @event-drag-create="openModal"
                @event-click="openModelInfo"
                @onEventDblclick="openModelInfo"
                :minCellWidth="100"
                @event-duration-change="handleChangeOnBoard"
                @event-drop="handleChangeOnBoard"
            >
                <template v-slot:controls="{ changeView }">
                    <div class="custom-buttons">
                        <button @click="changeView('day')">Day</button>
                        <button @click="changeView('week')">Week</button>
                        <button @click="changeView('month')">Month</button>
                    </div>
                </template>
            </vue-cal>
        </div>
        <a-modal v-model:open="modalVisible" @ok="handleOk" @cancel="cancelEvent">
            <div class="form-model">
                <a-input :value="newEvent.title" @update:value="newValue => newEvent.title = newValue" id="title"
                         placeholder="Add title" class="space"/>

                <a-time-range-picker
                    :value="[newEvent.startTime, newEvent.endTime]"
                    @update:value="updateTimeRange"
                    id="timeRange"
                    class="space"
                    style="width: 50%"
                />
                <a-input-number :value="reminder" @update:value=" newValue => reminder= newValue"
                                style="width: 45%; margin-left: 5%" class="space" placeholder="Reminder">
                    <template #addonAfter>
                        <a-select :value="typeTime" @update:value=" newValue => typeTime= newValue"
                                  style="width: 100px;">
                            <a-select-option value="Minutes">Minutes</a-select-option>
                            <a-select-option value="Hours">Hours</a-select-option>
                            <a-select-option value="Days">Days</a-select-option>
                        </a-select>
                    </template>
                </a-input-number>
                <a-input :value="newEvent.location" @update:value=" newValue => newEvent.location= newValue"
                         id="location" placeholder="Add location" class="space"/>
                <a-select
                    v-model:value="guest"
                    mode="multiple"
                    style="width: 100%; max-height: 200px"
                    placeholder="Please select"
                    :options="listUser"
                    @change="handleChange"
                    :filter-option="(input, option) => option.label.toLowerCase().indexOf(input.toLowerCase()) >= 0"
                />
            </div>
        </a-modal>
        <a-modal v-model:open="modelInfoVisible" @ok="updateEvent">
            <div class="form-model">
                <a-input :value="infoEvent.title" @update:value="newValue => infoEvent.title = newValue" id="title"
                         placeholder="Add title" class="space"/>
                <a-time-picker
                    v-model:value="infoEvent.startTime"
                    placeholder="Start Time"
                    class="space"
                    style="width: 23%; margin-right: 2%"
                />
                <a-time-picker
                    v-model:value="infoEvent.endTime"
                    placeholder="End Time"
                    class="space"
                    style="width: 22%;  margin-right: 4%"
                />

                <a-input-number :value="infoEvent.reminder" @update:value="newValue => infoEvent.reminder = newValue"
                                style="width: 45%; margin-left: 4%" class="space" placeholder="Reminder">
                    <template #addonAfter>
                        <a-select :value="infoEvent.typeTime" @update:value="newValue => infoEvent.typeTime = newValue"
                                  style="width: 100px;">
                            <a-select-option value="Minutes">Minutes</a-select-option>
                            <a-select-option value="Hours">Hours</a-select-option>
                            <a-select-option value="Days">Days</a-select-option>
                        </a-select>
                    </template>
                </a-input-number>

                <a-input :value="infoEvent.location" @update:value="newValue => infoEvent.location = newValue"
                         id="location" placeholder="Add location" class="space"/>

                <a-select
                    v-model:value="infoEvent.listGuest"
                    mode="multiple"
                    style="width: 100%; max-height: 200px"
                    placeholder="Please select"
                    :options="listUser"
                    @change="handleChange"
                    :filter-option="(input, option) => option.label.toLowerCase().indexOf(input.toLowerCase()) >= 0"
                />
            </div>
            <template #footer>
                <a-button key="delete" @click="deleteEvent" type="primary" danger>Delete</a-button>
                <a-button key="cancel" @click="cancelEvent">Cancel</a-button>
                <a-button key="ok" @click="updateEvent" type="primary">OK</a-button>
            </template>
        </a-modal>
    </div>
</template>

<script setup>
import VueCal from 'vue-cal'
import 'vue-cal/dist/vuecal.css'
import {createVNode, ref} from 'vue'
import dayjs from 'dayjs'
import userApi from '@/repositories/userApi.js'
import appointmentApi from '@/repositories/appointmentApi.js'
import {useMessageStore} from '@/stores/messageStore.js'
import {Modal} from "ant-design-vue";
import {ExclamationCircleOutlined} from "@ant-design/icons-vue";
import classApi from "@/repositories/classApi.js";

const reminder = ref()
const typeTime = ref('Minutes')
const modalVisible = ref(false)
const minEventWidth = ref(0)
const selected = ref('week')
const currentDate = ref('')
const listGuest = ref([])
const title = ref('')
const activeView = ref('week')
const modelInfoVisible = ref(false)
const messageStore = useMessageStore()
const newEvent = ref({
    title: '',
    startTime: dayjs(),
    endTime: dayjs(),
    location: '',
})
const infoEvent = ref({
    id: '',
    title: '',
    startTime: dayjs(),
    endTime: dayjs(),
    location: '',
    listGuest: [],
    reminder: '',
    typeTime: '',
    class: 'important'

})

const listUser = ref([])
const getListUser = () => {
    userApi.getListUser()
        .then(res => {
            listUser.value = res.data.map(user => ({
                value: user.id,
                label: user.name
            }))
        })
}
getListUser()

const getListAppointment = () => {
    appointmentApi.getListAppointment()
        .then(res => {
            events.value = res.data.map(event => ({
                title: event.title,
                start: new Date(event.start),
                end: new Date(event.end),
                location: event.location,
                listGuest: event.listGuest,
                reminder: event.reminder,
                typeTime: event.typeTime,
                formattedStart: formatTimestamp(event.start),
                formattedEnd: formatTimestamp(event.end),
                class: event.color,
                timeBefore: event.timeBefore,
                id: event.id
            }))
            console.log(events.value)

        })
        .catch(err => {
            messageStore.addMessage('error', err.response.data.message);
        });
}
getListAppointment()


const events = ref([])

const handleChangeView = view => {
    activeView.value = view
}


const handleOk = async () => {
    const startTimeTimestamp = new Date(dayjs(newEvent.value.startTime).valueOf())
    const endTimeTimestamp = new Date(dayjs(newEvent.value.endTime).valueOf())
    const guestArray = Array.from(listGuest.value)
    const reminderTmp = reminder.value + ' ' + typeTime.value

    const data = {
        name: newEvent.value.title,
        date_start: startTimeTimestamp,
        date_end: endTimeTimestamp,
        location: newEvent.value.location,
        list_guest: guestArray,
        reminder: reminderTmp
    }


    await appointmentApi.createAppointment(data)
        .then(response => {
            if (response.status === 200) {
                messageStore.addMessage('success', 'Create appointment successfully.')
                getListAppointment()
            }
        })
        .catch(err => {
            messageStore.addMessage('error', err.response.data.message)
        })
    modalVisible.value = false
}


const cancelEvent = () => {
    modelInfoVisible.value = false
}

const openModal = (event) => {
    modalVisible.value = true
    const startTime = event.start
    const endTime = event.end
    newEvent.value.startTime = dayjs(startTime)
    newEvent.value.endTime = dayjs(endTime)
}

const openModelInfo = (event) => {
    console.log(event)
    modelInfoVisible.value = true;
    infoEvent.value.title = event.title;
    infoEvent.value.id = event.id;
    infoEvent.value.startTime = dayjs(event.start);
    infoEvent.value.endTime = dayjs(event.end);
    infoEvent.value.location = event.location;
    infoEvent.value.listGuest = convertGuestsToNames(event.listGuest)
    infoEvent.value.reminder = event.reminder;
    infoEvent.value.typeTime = event.typeTime;
    infoEvent.value.formattedStart = formatTimestamp(event.start);
    infoEvent.value.formattedEnd = formatTimestamp(event.end);
}

const getCurrentDay = () => {
    return new Date().toLocaleDateString()
}
currentDate.value = getCurrentDay()

const handleChange = guest => {
    listGuest.value = guest
}

const updateTimeRange = (newValue) => {
    newEvent.value.startTime = newValue[0]
    newEvent.value.endTime = newValue[1]
}

const handleChangeOnBoard = (event) => {
    const reminderTmp = event.event.reminder + ' ' + event.event.typeTime
    const userIds = event.event.listGuest.map(guest => guest.userId);

    const data = {
        name: event.event.title,
        date_start: event.event.start,
        date_end: event.event.end,
        location: event.event.location,
        list_guest: userIds,
        reminder: reminderTmp,
        id: event.event.id
    }
    appointmentApi.updateDateStart(data)
        .then(response => {
            if (response.status === 200) {
                messageStore.addMessage('success', 'Update appointment successfully.')
                getListAppointment()
            }
            getListAppointment()
        })
        .catch(err => {
            getListAppointment()
        })

}

const formatTimestamp = (timestamp) => {
    return dayjs(timestamp).format('YYYY-MM-DD HH:mm')
}

const convertGuestsToNames = (guests) => {
    return guests.map(guest => guest.name)
}

// Phương thức xử lý xóa sự kiện
const deleteEvent = () => {
    const eventId = infoEvent.value.id


    Modal.confirm({
        title: 'Do you want to remove event?',
        icon: createVNode(ExclamationCircleOutlined),
        onOk() {
            appointmentApi.deleteAppointment(eventId)
                .then(res => {
                    messageStore.addMessage('success', 'Remove successfully.')
                    getListAppointment()
                })
                .catch(err => {
                    messageStore.addMessage('error', 'Something went wrong.')
                })
            modelInfoVisible.value = false
        },
        onCancel() {
            console.log('Cancel')
        },
    })
}

// Phương thức xử lý cập nhật sự kiện
const updateEvent = () => {
    const reminderTmp = infoEvent.value.reminder + ' ' + infoEvent.value.typeTime
    const startTimeTimestamp = new Date(dayjs(infoEvent.value.startTime).valueOf())
    const endTimeTimestamp = new Date(dayjs(infoEvent.value.endTime).valueOf())

    const userIds = infoEvent.value.listGuest.map(guest => guest.userId);


    const eventId = infoEvent.value.id;
    const updatedEvent = {
        id: eventId,
        name: infoEvent.value.title,
        date_start: startTimeTimestamp,
        date_end: endTimeTimestamp,
        location: infoEvent.value.location,
        list_guest: userIds,
        reminder: reminderTmp
    }
    console.log(updatedEvent)

    appointmentApi.updateDateStart(updatedEvent)
        .then(response => {
            if (response.status === 200) {
                messageStore.addMessage('success', 'Update appointment successfully.')
                getListAppointment()
            }
            getListAppointment()
        })
        .catch(err => {
            getListAppointment()
        })
    modelInfoVisible.value = false;

}

const checkTimeBefore = () => {
    const currentTime = new Date();
    events.value.forEach(event => {
        const timeBefore = new Date(event.timeBefore);
        const timeAfter = new Date(event.timeBefore);
        timeAfter.setMinutes(timeAfter.getMinutes() + 1);
        if (currentTime >= timeBefore && currentTime <= timeAfter) {
            messageStore.addMessage('warning', `${event.title} is about to start!`)
        }
    });
}
setInterval(checkTimeBefore, 60000);

</script>

<style>
.appointment-wrapper {
    padding: 10px;
    background-color: var(--color-white);
    border-radius: 10px;
    height: calc(100vh - 100px);
    overflow: hidden;
}

.select-view {
    margin: 10px;
}

.calendar-container {
    overflow-y: auto;
    height: 86vh
}

.calendar-scrollable {
    overflow-x: hidden
}

.form-model {
    padding: 30px 10px;
}

#name {
    border: none;
    border-bottom: 2px var(--color-blue) solid;
    border-radius: inherit;
    font-size: 18px;
    font-weight: bold;
    color: var(--color-blue);
}

#name:focus-visible, #name:focus {
    border-color: var(--color-white);
    border-bottom: 2px var(--color-blue) solid;
    box-shadow: none;
}

.space {
    margin-bottom: 20px;
    box-shadow: none;
}

.vuecal__menu,
.vuecal__cell-events-count {
    background-color: var(--color-white);
}

.vuecal__title-bar {
    background-color: var(--color-white);
    line-height: 60px;
}

.vuecal__cell--today,
.vuecal__cell--current {
    background-color: rgb(210, 227, 252);
}

.vuecal__cell--selected:before,
.vuecal:not(.vuecal--day-view) .vuecal__cell--selected {
    background-color: rgba(255, 255, 255, 0);
}

.vuecal__cell--highlighted:not(.vuecal__cell--has-splits),
.vuecal__cell-split--highlighted {
    background-color: rgb(210, 227, 252);
}

.vuecal__arrow.vuecal__arrow--highlighted,
.vuecal__view-btn.vuecal__view-btn--highlighted {
    background-color: var(--color-blue);
}


.vuecal--month-view .vuecal__cell {
    height: 200px;
    width: 150px;
}

.vuecal__time-cell-label {
    font-size: 8px;
}


.vuecal--month-view .vuecal__cell-content {
    justify-content: flex-start;
    height: 100%;
    align-items: flex-end;
}

.vuecal--month-view .vuecal__cell-date {
    padding: 4px;
}

.vuecal--month-view .vuecal__no-event {
    display: none;
}

.vuecal__cell-events-count {
    width: 18px;
    height: 2px;
    color: transparent;
}

.vuecal__cell-events-count {
    width: 4px;
    min-width: 0;
    height: 4px;
    padding: 0;
    color: transparent;
}

.vuecal__cell--has-events {
    background-color: rgba(255, 255, 255, 0);
}

.vuecal__cell-events-count {
    display: none;
}

// Customer

.vuecal__event {
    cursor: pointer;
}

.student {
    background-color: var(--color-blue);
    color: #fff !important;
    border: 1px solid var(--color-white) !important;
}

.important {
    background-color: #B62F35FF;
    color: #fff !important;
    border: 1px solid var(--color-white) !important;

}


.vuecal__event-title {
    font-size: 1.2em;
    font-weight: bold;
    margin: 4px 0 8px;
}

.vuecal__event-time {
    display: inline-block;
    margin-bottom: 12px;
    padding-bottom: 12px;
    border-bottom: 1px solid rgba(0, 0, 0, 0.2);
}

.vuecal__event-content {
    font-style: italic;
}


</style>

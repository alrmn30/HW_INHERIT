package org.example.my;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

import java.sql.SQLOutput;
import java.util.Arrays;

public class TodosTest {

    @Test
    public void shouldGetTaskId() {
        Task task = new Task(1);

        int expected = 1;
        int actual = task.getId();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldGetSimpleTaskTitle() {
        SimpleTask simpleTask = new SimpleTask(2, "Simple task title");

        String expected = "Simple task title";
        String actual = simpleTask.getTitle();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldGetEpicSubtasks() {
        String[] subtasks = new String[] {"first subtask", "second subtask", "third subtask"};
        Epic epic = new Epic(3, subtasks);

        String[] expected = subtasks;
        String[] actual = epic.getSubtasks();

        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldGetMeetingTopic() {
        Meeting meeting = new Meeting(4, "Meeting topic", "Meeting project", "Meeting start");

        String expected = "Meeting topic";
        String actual = meeting.getTopic();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldGetMeetingProject() {
        Meeting meeting = new Meeting(4, "Meeting topic", "Meeting project", "Meeting start");

        String expected = "Meeting project";
        String actual = meeting.getProject();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldGetMeetingStart() {
        Meeting meeting = new Meeting(4, "Meeting topic", "Meeting project", "Meeting start");

        String expected = "Meeting start";
        String actual = meeting.getStart();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldAddSimpleTask() {
        Todos todos = new Todos();

        SimpleTask simpleTask = new SimpleTask(1, "Вынести мусор");

        todos.add(simpleTask);

        Task[] expected = { simpleTask };
        Task[] actual = todos.getTasks();

        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldAddEpic() {
        Todos todos = new Todos();

        String[] subtasks = { "яйца", "молоко", "хлеб", "сыр" };
        Epic epic = new Epic(2, subtasks);

        todos.add(epic);

        Task[] expected = { epic };
        Task[] actual = todos.getTasks();

        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldAddMeeting() {
        Todos todos = new Todos();

        Meeting meeting = new Meeting(3, "Прогулка с друзьями", "Отдых", "1.06.23; 15:30" );

        todos.add(meeting);

        Task[] expected = { meeting };
        Task[] actual = todos.getTasks();

        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldAddThreeTasksOfDifferentType() {

        SimpleTask simpleTask = new SimpleTask(1, "Вынести мусор");

        String[] subtasks = { "яйца", "молоко", "хлеб", "сыр" };
        Epic epic = new Epic(2, subtasks);

        Meeting meeting = new Meeting(3, "Прогулка с друзьями", "Отдых", "1.06.23; 15:30" );

        Todos todos = new Todos();
        todos.add(simpleTask);
        todos.add(epic);
        todos.add(meeting);

        Task[] expected = { simpleTask, epic, meeting };
        Task[] actual = todos.getTasks();

        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldMatchQueryInSimpleTask() {
        Todos todos = new Todos();
        SimpleTask simpleTask = new SimpleTask(1, "Вынести мусор");
        todos.add(simpleTask);

        boolean expected = true;
        boolean actual = simpleTask.matches("мусор");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldNotMatchQueryInSimpleTask() {
        Todos todos = new Todos();
        SimpleTask simpleTask = new SimpleTask(1, "Вынести мусор");
        todos.add(simpleTask);

        boolean expected = false;
        boolean actual = simpleTask.matches("купить");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldMatchQueryInEpic() {
        Todos todos = new Todos();
        String[] subtasks = { "яйца", "молоко", "хлеб", "сыр" };
        Epic epic = new Epic(2, subtasks);
        todos.add(epic);

        boolean expected = true;
        boolean actual = epic.matches("хлеб");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldNotMatchQueryInEpic() {
        Todos todos = new Todos();
        String[] subtasks = { "яйца", "молоко", "хлеб", "сыр" };
        Epic epic = new Epic(2, subtasks);
        todos.add(epic);

        boolean expected = false;
        boolean actual = epic.matches("купить");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldMatchQueryMeetingTopic() {
        Todos todos = new Todos();
        Meeting meeting = new Meeting(3, "Прогулка с друзьями", "Отдых", "1.06.23; 15:30" );
        todos.add(meeting);

        boolean expected = true;
        boolean actual = meeting.matches("друзьями");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldNotMatchQueryMeetingTopic() {
        Todos todos = new Todos();
        Meeting meeting = new Meeting(3, "Прогулка с друзьями", "Отдых", "1.06.23; 15:30" );
        todos.add(meeting);

        boolean expected = false;
        boolean actual = meeting.matches("работа");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldMatchQueryMeetingProject() {
        Todos todos = new Todos();
        Meeting meeting = new Meeting(3, "Прогулка с друзьями", "Отдых", "1.06.23; 15:30" );
        todos.add(meeting);

        boolean expected = true;
        boolean actual = meeting.matches("Отдых");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldNotMatchQueryMeetingProject() {
        Todos todos = new Todos();
        Meeting meeting = new Meeting(3, "Прогулка с друзьями", "Отдых", "1.06.23; 15:30" );
        todos.add(meeting);

        boolean expected = false;
        boolean actual = meeting.matches("собака");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldMatchQueryInSimpleTaskAmongAll() {

        SimpleTask simpleTask = new SimpleTask(1, "Вынести мусор");

        String[] subtasks = { "яйца", "молоко", "хлеб", "сыр" };
        Epic epic = new Epic(2, subtasks);

        Meeting meeting = new Meeting(3, "Прогулка с друзьями", "Отдых", "1.06.23; 15:30" );

        Todos todos = new Todos();
        todos.add(simpleTask);
        todos.add(epic);
        todos.add(meeting);

        Task[] expected = { simpleTask };
        Task[] actual = todos.search("мусор");

        Assertions.assertArrayEquals(expected, actual);
    }
    @Test
    public void shouldMatchQueryInEpicAmongAll() {

        SimpleTask simpleTask = new SimpleTask(1, "Вынести мусор");

        String[] subtasks = { "яйца", "молоко", "хлеб", "сыр" };
        Epic epic = new Epic(2, subtasks);

        Meeting meeting = new Meeting(3, "Прогулка с друзьями", "Отдых", "1.06.23; 15:30" );

        Todos todos = new Todos();
        todos.add(simpleTask);
        todos.add(epic);
        todos.add(meeting);

        Task[] expected = { epic };
        Task[] actual = todos.search("хлеб");

        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldMatchQueryInMeetingTopicAmongAll() {

        SimpleTask simpleTask = new SimpleTask(1, "Вынести мусор");

        String[] subtasks = { "яйца", "молоко", "хлеб", "сыр" };
        Epic epic = new Epic(2, subtasks);

        Meeting meeting = new Meeting(3, "Прогулка с друзьями", "Отдых", "1.06.23; 15:30" );

        Todos todos = new Todos();
        todos.add(simpleTask);
        todos.add(epic);
        todos.add(meeting);

        Task[] expected = { meeting };
        Task[] actual = todos.search("друзьями");

        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldMatchQueryInMeetingProjectAmongAll() {

        SimpleTask simpleTask = new SimpleTask(1, "Вынести мусор");

        String[] subtasks = { "яйца", "молоко", "хлеб", "сыр" };
        Epic epic = new Epic(2, subtasks);

        Meeting meeting = new Meeting(3, "Прогулка с друзьями", "Отдых", "1.06.23; 15:30" );

        Todos todos = new Todos();
        todos.add(simpleTask);
        todos.add(epic);
        todos.add(meeting);

        Task[] expected = { meeting };
        Task[] actual = todos.search("Отдых");

        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldMatchQueryInEveryTypeAmongAll() {

        SimpleTask simpleTask = new SimpleTask(1, "Придумать слово, которое будет повторяться во всех задачах");

        String[] subtasks = { "вымыть посуду", "вытереть пыль с подоконника", "я не придумала это слово", "вынести мусор" };
        Epic epic = new Epic(2, subtasks);

        Meeting meeting = new Meeting(3, "и вообще, что за пытки такие", "как будто придумать такое слово вот прям так просто", "1.06.23; 15:30" );

        Todos todos = new Todos();
        todos.add(simpleTask);
        todos.add(epic);
        todos.add(meeting);

        Task[] expected = { simpleTask, epic, meeting };
        Task[] actual = todos.search("слово");

        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldMatchQueryInTwoTypesAmongAll() {

        SimpleTask simpleTask = new SimpleTask(1, "Придумать слово, которое будет повторяться во всех задачах");

        String[] subtasks = { "вымыть посуду", "вытереть пыль с подоконника", "я не придумала это слово", "вынести мусор" };
        Epic epic = new Epic(2, subtasks);

        Meeting meeting = new Meeting(3, "и вообще, что за пытки такие", "как будто придумать такое вот прям так просто", "1.06.23; 15:30" );

        Todos todos = new Todos();
        todos.add(simpleTask);
        todos.add(epic);
        todos.add(meeting);

        Task[] expected = { simpleTask, epic };
        Task[] actual = todos.search("слово");

        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldNotMatchQueryAmongAll() {

        SimpleTask simpleTask = new SimpleTask(1, "Вынести мусор");

        String[] subtasks = { "яйца", "молоко", "хлеб", "сыр" };
        Epic epic = new Epic(2, subtasks);

        Meeting meeting = new Meeting(3, "Прогулка с друзьями", "Отдых", "1.06.23; 15:30" );

        Todos todos = new Todos();
        todos.add(simpleTask);
        todos.add(epic);
        todos.add(meeting);

        Task[] expected = new Task[0];
       Task[] actual = todos.search("слово");

        Assertions.assertArrayEquals(expected, actual);
    }
    
}

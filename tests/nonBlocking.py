#!/usr/bin/python3

from nonblock import nonblock_read

pipe = subprocess.Popen(['someProgram'], stdout=subprocess.PIPE)

while True:

    data = nonblock_read(pipe.stdout)

    if data is None:

        # All data has been processed and subprocess closed stream

        pipe.wait()

        break

    elif data:

        # Some data has been read, process it

        processData(data)

    else:

        # No data is on buffer, but subprocess has not closed stream

        idleTask()

# All data has been processed, focus on the idle task

idleTask()

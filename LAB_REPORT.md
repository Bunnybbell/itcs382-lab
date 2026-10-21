# Lab Report, Code Quality and Design Quality

Repository URL:https://github.com/Bunnybbell/itcs382-lab

## Tools

| Item | Version or name |
| --- | --- |
| JDK | Eclipse Temurin OpenJDK 25.0.4.1 (LTS) |
| Maven |Apache Maven 3.10.0 |
| IDE |Visual Studio Code 1.143.0 (User Setup) |
| SonarQube for IDE |6.1.1 |
| AI tool and exact model |ChatGPT (GPT-6) |

## Task 1. SonarQube on the starter code

Commit of the starter, before any change: 7586cdf

Table 1. SonarQube issues in the starter code

| No. | Rule | File:line | Message |
| --- | --- | --- | --- |
| 1 |java:S106|Main.java:9|Replace this use of System.out by a logger.|
| 2 |java:S106|Main.java:15|Replace this use of System.out by a logger.|
| 3 |java:S106|Main.java:17|Replace this use of System.out by a logger.|
| 4 |java:S106|Main.java:22|Replace this use of System.out by a logger.|

## Task 2. AI review of the starter code

| Item | Value |
| --- | --- |
| Date |21/10/2026 |
| AI tool and exact model |ChatGPT (GPT-6)|
| Commit reviewed |7586cdf|
| How the AI read the files (attached the files, opened the folder, or pasted the files) |Attached 4 Java files directly to ChatGPT (Locker.java, Main.java, Parcel.java, ParcelService.java)|
| Output file in the reviews folder |reviews/parcelpoint-review.md|

Table 2. All findings on the starter code

| No. | File:line | Finding | Found by (SonarQube, AI or both) | SonarQube rule or course term | Correct? (yes or no, and why) |
| --- | --- | --- | --- | --- | --- |
| 1 | Main.java:9 | System.out used instead of a logger | SonarQube | java:S106 | Yes – System.out is used, but console output is intentional in this CLI application. |
| 2 | Main.java:15 | System.out used instead of a logger | SonarQube | java:S106 | Yes – System.out is used to display the store result. |
| 3 | Main.java:17 | System.out used instead of a logger | SonarQube | java:S106 | Yes – System.out is used to display the pickup fee. |
| 4 | Main.java:22 | System.out used instead of a logger | SonarQube | java:S106 | Yes – System.out is used to display locker information. |
| 5 | Main.java:6–29 | Scanner is never closed | AI | Resource handling | Yes – The Scanner is created without being closed. |
| 6 | ParcelService.java:74–81 | FileWriter is never closed | AI | Resource handling | Yes – The writer is not closed, which may cause data loss. |
| 7 | ParcelService.java:42 | Strings are compared using `==` instead of `.equals()` | AI | String comparison | Yes – `==` compares references rather than string contents. |
| 8 | Main.java:11–17 | Input arguments are not validated | AI | Input validation | Yes – Missing arguments may cause exceptions. |
| 9 | ParcelService.java:39–54 | Returning 0 for a missing parcel is ambiguous | AI | Ambiguous result | Yes – A valid pickup can also have a zero fee. |
| 10 | ParcelService.java:75–81 | Empty catch block ignores IOException | AI | Error handling | Yes – Errors are silently ignored. |
| 11 | ParcelService.java:6 | Unused Date import | AI | Unused import | Yes – Date is imported but never used. |
| 12 | ParcelService.java:12 | Unused maxParcels field | AI | Unused field | Yes – The field is declared but never read. |
| 13 | Main.java:6–25 | Multiple commands handled in one method | AI | Logical cohesion | No – Command dispatch is an intended responsibility of the CLI entry point; branching alone does not prove poor design. |
| 14 | ParcelService.java:64–71 | Boolean parameter controls different reporting behaviours | AI | Logical cohesion | Yes – The flag selects between reporting behaviours. |
| 15 | ParcelService.java:24–80 | Class handles storage, fees, notifications and file persistence | AI | Class cohesion | Yes – Multiple responsibilities are combined in one class. |
| 16 | Main.java:19–21; ParcelService.java:64–68 | Boolean argument controls ParcelService behaviour | AI | Control coupling | Yes – The caller controls which reporting branch executes. |
| 17 | Parcel.java:7 | Public status field allows direct modification | AI | Content coupling | Yes – Other classes can directly access the field. |
| 18 | ParcelService.java:20–21; Main.java:22 | Internal locker collection is returned directly | AI | Strong coupling / collection exposure | Yes – Callers can modify the internal list. |
| 19 | ParcelService.java:52–59 | Entire Parcel object passed when only size is needed | AI | Stamp coupling | Yes – The fee method only requires the parcel size. |
| 20 | Parcel.java:13; ParcelService.java:28,44,68 | Hardcoded strings represent parcel statuses | AI | Connascence of convention | Yes – Classes depend on matching status strings. |
| 21 | Locker.java:5; Parcel.java:6 | Shared numeric codes represent parcel sizes | AI | Connascence of convention | Yes – Classes must agree on size codes 1, 2 and 3. |
| 22 | Main.java:13; Parcel.java:9–12 | Parcel constructor depends on argument order | AI | Connascence of position | Yes – Two String arguments can be accidentally swapped. |
| 23 | ParcelService.java:15–17; Locker.java:8–10 | Locker constructor depends on integer argument order | AI | Connascence of position | Yes – Swapping the two integers would still compile. |
| 24 | ParcelService.java:36; Main.java:15 | Null represents no available locker | AI | Connascence of convention | Yes – Both classes must understand the meaning of null. |
  

### 2.1 Name two problems that only the AI review found.
- Incorrect String Comparison: In ParcelService.java, the code uses == instead of .equals() to compare strings. This can cause problems because == checks whether two objects are the same, not whether their text is the same.
- Control Coupling: In Main.java and ParcelService.java, a Boolean value is used to control how the method works. This creates control coupling because one class controls the behavior of another class.
### 2.2 Name one problem that only SonarQube found, or write none.
SonarQube found that `Main.java` uses `System.out` instead of a logger (java:S106), but the AI did not mention this problem. However, I think using `System.out` is reasonable because this is a command-line program.
### 2.3 Name one AI finding that was wrong or too vague, and explain why. If every finding was right, explain how you checked them.
I think the AI finding about logical cohesion in `Main.java` was too general. The AI said that handling different commands in one method is a design problem. However, I think it makes sense because the main method needs to handle user commands in this program. The code could be improved, but I don't think this automatically means the design is bad.
## Task 3. Fix and check

Table 3. What you did with each SonarQube issue in Table 1

| No. in Table 1 | Fixed or kept | Commit, or the reason for keeping it |
| --- | --- | --- |
| 1 | Kept | `System.out.println()` displays instructions to users in this command-line application. |
| 2 | Kept | `System.out.println()` displays the locker number after storing a parcel. This is intended CLI output. |
| 3 | Kept | `System.out.println()` displays the pickup fee to the user. A logger is not necessary for this output. |
| 4 | Kept | `System.out.println()` displays the number of free lockers. This is intended CLI output. |


Number of SonarQube issues that remain after your fixes:4

The two design problems that you fixed

| Design problem (course term) | What you changed | Commit |
| --- | --- | --- |
| Control Coupling | I replaced `printParcels(boolean forStaff)` with two separate methods, `printStaffParcels()` and `printWaitingParcels()`, to remove the Boolean flag that controlled the method's behavior. | `88c7488` |
| Content Coupling | I changed `Parcel.status` from public to private, added `getStatus()` and `setStatus()`, and updated `ParcelService.java` to use these methods instead of accessing the status directly. | `0fee7da` |

Output of the program after your fixes, for the commands `store P1 6501 2`, `pickup P1 5` and `staff`, and the content of parcels.csv:

```text
Commands: store <code> <studentId> <size 1-3>, pickup <code> <days>, list, staff, quit
store P1 6501 2
SMS to 6501: parcel P1 is in locker 2
Locker 2
pickup P1 5
Fee: 30.0
staff
P1 6501 PICKED_UP
Free lockers: 3
quit

parcels.csv:
P1,PICKED_UP
```

## Task 4. AI-generated poor and good designs

| Item | bikeshare-poor | bikeshare-good |
| --- | --- | --- |
| Date |22/10/2026|22/10/2026 |
| AI tool and exact model |ChatGPT (GPT-6) |ChatGPT (GPT-6) |
| Prompt (B or C as given, or your changed prompt in full) |Prompt B as given |Prompt C as given |
| Builds with mvn package (yes or no) |Yes | Yes|
| Number of classes, interfaces, enums and records |1 class, 0 interfaces, 0 enums, 0 records |6 classes, 1 interface, 2 enums, 2 records|
| SonarQube issues, total | 38 | 9  |
| SonarQube issues, by rule |java:S106 (27), java:S2386 (5), java:S1192 (2), java:S1220 (1), java:S3776 (1), java:S6213 (1), java:S135 (1) | java:S106 (9) |
| AI review findings in Check 1 |9 |5|
| AI review findings in Check 2 |11|3 |
| Review output file in the reviews folder | reviews/bikeshare-poor-review.md |reviews/bikeshare-good-review.md |

4.1 Did SonarQube report any cohesion or coupling problem in bikeshare-poor? Quote the rule and the message if it did.
No, SonarQube did not directly find any cohesion or coupling problems in bikeshare-poor. However, it found java:S3776: "Refactor this method to reduce its Cognitive Complexity." This shows that the method is too complicated, but it is not directly about cohesion or coupling. The AI review was able to find these design problems.
4.2 Did the AI review tell the two versions apart? Give one finding for bikeshare-poor that does not appear for bikeshare-good.
Yes, the AI found 20 problems in bikeshare-poor, but only 8 in bikeshare-good. One example is that bikeshare-poor uses public static fields to store shared data. This can cause common coupling because different parts of the program depend on the same data. The AI did not find this problem in bikeshare-good because the fields are private.
4.3 Which version would you rather extend with a new member type, such as a visitor who pays 4 baht per minute, and why?
I would choose bikeshare-good because the code is easier to understand and modify. It uses an enum for member types and has a separate class for calculating fees. If I wanted to add a visitor who pays 4 baht per minute, I would mainly need to update the enum and fee calculation. In bikeshare-poor, the code uses integer codes and shared data, so making changes would be more difficult.
## Task 5. Reflection

5.1 What does SonarQube check, and what does it not check? Answer with examples from this lab.
SonarQube checks code quality problems such as duplicated strings, public static fields, and complex methods. For example, it found 38 issues in bikeshare-poor, including java:S2386 and java:S3776. However, it cannot fully check design problems like cohesion and coupling. We still need to use AI reviews and our own understanding to check these problems.
5.2 When should you not trust the AI review? Give an example from this lab.
I should not trust AI reviews without checking the actual code first because AI can sometimes give unclear or incorrect findings. For example, the AI said that handling different commands in Main.java of ParcelPoint was a logical cohesion problem. However, I think it is normal for a command-line program to handle user commands in the main method. This shows that I should check AI findings before deciding whether they are correct.
5.3 What will you do differently in Project Phase 2 after this lab?
In Project Phase 2, I will try to design classes with clear responsibilities instead of putting everything in one class. I will also pay more attention to cohesion, coupling, and encapsulation. Before submitting my code, I will use SonarQube and AI reviews to find possible problems and check them myself.

## AI usage declaration

| AI tool and exact model | Purpose | Files or parts that it produced or changed | How you checked its output |
| --- | --- | --- | --- |
| ChatGPT (GPT-6) | Review ParcelPoint code and find code quality and design problems. | `reviews/parcelpoint-review.md` | Compared the AI findings with the Java code and SonarQube results. |
| ChatGPT (GPT-6) | Help fix problems in ParcelPoint. | `ParcelService.java`, `Parcel.java`, `Main.java` | Ran Maven and tested the parcel storage, pickup, fee calculation, and CSV file. |
| ChatGPT (GPT-6) | Generate poor BikeShare code using Prompt B. | `bikeshare-poor/pom.xml`, `bikeshare-poor/src/main/java/BikeShare.java` | Built and tested the program and checked SonarQube findings. |
| ChatGPT (GPT-6) | Generate good BikeShare code using Prompt C. | `bikeshare-good/pom.xml` and 11 Java files | Built and tested the program and checked SonarQube findings. |
| ChatGPT (GPT-6) | Review both BikeShare versions using Prompt A. | `reviews/bikeshare-poor-review.md`, `reviews/bikeshare-good-review.md` | Compared the AI findings with the code and SonarQube results. |
| ChatGPT (GPT-6) | Help write and organize the lab report. | `LAB_REPORT.md` | Checked the answers against the actual results and Git commits. |